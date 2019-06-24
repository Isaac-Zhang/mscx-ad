package com.sxzhongf.ad.index.keyword;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * UnitKeywordIndexAwareImpl for 推广单元<->关键词索引实现类
 * 使用倒排索引实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Slf4j
@Component
public class UnitKeywordIndexAwareImpl implements IIndexAware<String, Set<Long>> {

    //创建索引对象
    //一个关键词在N个推广单元中
    private static Map<String, Set<Long>> keywordRelationUnitsMap;
    //一个推广单元对应N个关键词
    private static Map<Long, Set<String>> unitRelationKeywordsMap;

    static {
        keywordRelationUnitsMap = new ConcurrentHashMap<>();
        unitRelationKeywordsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordRelationUnitsMap.get(key);
        if (null == result) {
            return Collections.emptySet();
        }

        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndexAwareImpl::unitRelationKeywordsMap, before add :{}", unitRelationKeywordsMap);

        //添加关键词对应的推广单元id Set
        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key, keywordRelationUnitsMap, ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        // 循环添加 每个推广单元对应的 关键词Set
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(
                    unitId, unitRelationKeywordsMap, ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }

        log.info("UnitKeywordIndexAwareImpl::unitRelationKeywordsMap, after add :{}", unitRelationKeywordsMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.info("UnitKeywordIndexAwareImpl::unitRelationKeywordsMap, can not support update,因为会遍历Set，直接删除后重新添加");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndexAwareImpl::unitRelationKeywordsMap, before delete :{}", unitRelationKeywordsMap);
        //根据key获取全量的 推广单元ids,然后移除部分或者全部（value）
        Set<Long> unitIds = CommonUtils.getOrCreate(key, keywordRelationUnitsMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            //根据UnitId获取keyword Set
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitRelationKeywordsMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndexAwareImpl::unitRelationKeywordsMap, after delete :{}", unitRelationKeywordsMap);

    }

    /**
     * 查看索引中是否存在当前推广单元和关键词信息
     */
    public boolean match(Long unitId, List<String> keywords) {
        //判断当前索引中是否存在当前推广计划
        if (unitRelationKeywordsMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitRelationKeywordsMap.get(unitId))) {
            Set<String> unitKeywords = unitRelationKeywordsMap.get(unitId);

            //当切仅当keywords 是unitKeywords 的子集合的时候返回true
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }

        return false;
    }
}

package com.sxzhongf.ad.index.hobby;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * UnitHobbyIndexAwareImpl for 推广单元<->兴趣索引实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Slf4j
@Component
public class UnitHobbyIndexAwareImpl implements IIndexAware<String, Set<Long>> {

    //反向索引，根据兴趣标签查询关联的 unit ids
    private static Map<String, Set<Long>> hobbyRelationUnitsMap;
    private static Map<Long, Set<String>> unitRelationHobbiesMap;

    static {
        hobbyRelationUnitsMap = new ConcurrentHashMap<>();
        unitRelationHobbiesMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return hobbyRelationUnitsMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitHobbyIndexAwareImpl::unitRelationHobbiesMap, before add :{}", unitRelationHobbiesMap);
        //获取兴趣标签对应的推广单元id Set
        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key, hobbyRelationUnitsMap, ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        // 循环添加 每个兴趣标签对应的 关键词Set
        for (Long hobbyId : value) {
            Set<String> hobbySet = CommonUtils.getOrCreate(
                    hobbyId, unitRelationHobbiesMap, ConcurrentSkipListSet::new
            );
            hobbySet.add(key);
        }
        log.info("UnitHobbyIndexAwareImpl::unitRelationHobbiesMap, after add :{}", unitRelationHobbiesMap);

    }

    @Override
    public void update(String key, Set<Long> value) {
        log.info("UnitHobbyIndexAwareImpl::unitRelationHobbiesMap, can not support update,因为会遍历Set，直接删除后重新添加");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitHobbyIndexAwareImpl::unitRelationHobbiesMap, before delete :{}", unitRelationHobbiesMap);
        //根据key获取全量的 推广单元ids,然后移除部分或者全部（value）
        Set<Long> unitIds = CommonUtils.getOrCreate(key, hobbyRelationUnitsMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            //根据UnitId获取hobby Set
            Set<String> hobbySet = CommonUtils.getOrCreate(unitId, unitRelationHobbiesMap, ConcurrentSkipListSet::new);
            hobbySet.remove(key);
        }
        log.info("UnitHobbyIndexAwareImpl::unitRelationHobbiesMap, after delete :{}", unitRelationHobbiesMap);

    }

    /**
     * 查看索引中是否存在当前推广单元和关键词信息
     */
    public boolean match(Long unitId, List<String> unitHobbies) {
        //判断当前索引中是否存在当前推广计划
        if (unitRelationHobbiesMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitRelationHobbiesMap.get(unitId))) {
            Set<String> unitHobbiesSet = unitRelationHobbiesMap.get(unitId);

            //当切仅当unitHobbies unitHobbiesSet 的子集合的时候返回true
            return CollectionUtils.isSubCollection(unitHobbies, unitHobbiesSet);
        }

        return false;
    }
}

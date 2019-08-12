package com.sxzhongf.ad.index.creative_relation_unit;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.index.adunit.AdUnitIndexObject;
import com.sxzhongf.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * CreativeRelationUnitIndexAwareImpl for 创意<->推广单元关联实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Slf4j
@Component
public class CreativeRelationUnitIndexAwareImpl implements IIndexAware<String, CreativeRelationUnitIndexObject> {

    /**
     * key使用${adId}-${unitId}
     * 正向索引
     */
    private static Map<String, CreativeRelationUnitIndexObject> objectMap;
    /**
     * 一个创意可对应多个推广单元
     * 反向索引
     */
    private static Map<Long, Set<Long>> creativeRelationUnitMap;

    /**
     * 一个推广单元可对应多创意
     * 反向索引
     */
    private static Map<Long, Set<Long>> unitRelationCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeRelationUnitMap = new ConcurrentHashMap<>();
        unitRelationCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeRelationUnitIndexObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeRelationUnitIndexObject value) {
        log.info("CreativeRelationUnitIndexAwareImpl:: before add :{}", objectMap);
        objectMap.put(key, value);

        //根据创意ID获取出所有关联的 推广单元ID
        Set<Long> unitSet = creativeRelationUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeRelationUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        //根据推广单元ID 获取 创意ID
        Set<Long> creativeSet = unitRelationCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitRelationCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());

        log.info("CreativeRelationUnitIndexAwareImpl:: after add :{}", objectMap);
    }

    @Override
    public void update(String key, CreativeRelationUnitIndexObject value) {
        log.info("CreativeRelationUnitIndexAwareImpl:: not support update.");
    }

    @Override
    public void delete(String key, CreativeRelationUnitIndexObject value) {
        log.info("CreativeRelationUnitIndexAwareImpl:: before add :{}", objectMap);
        objectMap.remove(key);
        //根据创意ID获取出所有关联的 推广单元ID
        Set<Long> unitSet = creativeRelationUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        //根据推广单元ID 获取 创意ID
        Set<Long> creativeSet = unitRelationCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }

        log.info("CreativeRelationUnitIndexAwareImpl:: after add :{}", objectMap);
    }

    /**
     * 通过推广单元id获取推广创意id
     */
    public List<Long> selectAdCreativeIds(List<AdUnitIndexObject> unitIndexObjects) {
        if (CollectionUtils.isEmpty(unitIndexObjects)) return Collections.emptyList();

        //获取要返回的广告创意ids
        List<Long> result = new ArrayList<>();
        for (AdUnitIndexObject unitIndexObject : unitIndexObjects) {
            //根据推广单元id获取推广创意
            Set<Long> adCreativeIds = unitRelationCreativeMap.get(unitIndexObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adCreativeIds)) result.addAll(adCreativeIds);
        }

        return result;
    }
}

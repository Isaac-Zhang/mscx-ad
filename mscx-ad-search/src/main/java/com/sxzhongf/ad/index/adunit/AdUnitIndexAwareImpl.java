package com.sxzhongf.ad.index.adunit;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.index.adplan.AdPlanIndexObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AdUnitIndexAwareImpl for 推广单元索引实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Slf4j
@Component
public class AdUnitIndexAwareImpl implements IIndexAware<Long, AdUnitIndexObject> {

    private static Map<Long, AdUnitIndexObject> objectMap;

    /**
     * 为了防止多线程造成的线程不安全问题，我们不能使用hashmap，需要实现ConcurrentHashMap
     */
    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 过滤当前是否存在满足positionType的UnitIds
     */
    public Set<Long> match(Integer positionType) {
        Set<Long> adUnitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (AdUnitIndexObject.isAdSlotType(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    /**
     * 根据UnitIds查询AdUnit list
     */
    public List<AdUnitIndexObject> fetch(Collection<Long> adUnitIds) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.EMPTY_LIST;
        }
        List<AdUnitIndexObject> result = new ArrayList<>();
        adUnitIds.forEach(id -> {
            AdUnitIndexObject object = get(id);
            if (null == object) {
                log.error("AdUnitIndexObject does not found:{}", id);
                return;
            }
            result.add(object);
        });

        return result;
    }

    @Override
    public AdUnitIndexObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitIndexObject value) {

        log.info("AdUnitIndexAwareImpl before add::{}", objectMap);
        objectMap.put(key, value);
        log.info("AdUnitIndexAwareImpl after add::{}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitIndexObject value) {
        log.info("AdUnitIndexAwareImpl before update::{}", objectMap);

        AdUnitIndexObject oldObj = objectMap.get(key);
        if (null == oldObj) {
            objectMap.put(key, value);
        } else {
            oldObj.update(value);
        }

        log.info("AdUnitIndexAwareImpl after update::{}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitIndexObject value) {
        log.info("AdUnitIndexAwareImpl before delete::{}", objectMap);
        objectMap.remove(key);
        log.info("AdUnitIndexAwareImpl after delete::{}", objectMap);
    }
}

package com.sxzhongf.ad.index.adunit;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.index.adplan.AdPlanIndexObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
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

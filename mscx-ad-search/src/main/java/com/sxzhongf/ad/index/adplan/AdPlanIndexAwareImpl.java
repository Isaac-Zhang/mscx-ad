package com.sxzhongf.ad.index.adplan;

import com.sxzhongf.ad.index.IIndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AdPlanIndexAwareImpl for 推广计划索引实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Slf4j
@Component
public class AdPlanIndexAwareImpl implements IIndexAware<Long, AdPlanIndexObject> {

    private static Map<Long, AdPlanIndexObject> planIndexObjectMap;

    /**
     * 为了防止多线程造成的线程不安全问题，我们不能使用hashmap，需要实现ConcurrentHashMap
     */
    static {
        planIndexObjectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanIndexObject get(Long key) {
        return planIndexObjectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanIndexObject value) {

        log.info("AdPlanIndexAwareImpl before add::{}", planIndexObjectMap);
        planIndexObjectMap.put(key, value);
        log.info("AdPlanIndexAwareImpl after add::{}", planIndexObjectMap);
    }

    @Override
    public void update(Long key, AdPlanIndexObject value) {

        log.info("AdPlanIndexAwareImpl before update::{}", planIndexObjectMap);

        //查询当前的索引信息，如果不存在，直接新增索引信息
        AdPlanIndexObject oldObj = planIndexObjectMap.get(key);
        if (null == oldObj) {
            planIndexObjectMap.put(key, value);
        } else {
            oldObj.update(value);
        }

        log.info("AdPlanIndexAwareImpl after update::{}", planIndexObjectMap);
    }

    @Override
    public void delete(Long key, AdPlanIndexObject value) {

        log.info("AdPlanIndexAwareImpl before delete::{}", planIndexObjectMap);
        planIndexObjectMap.remove(key);
        log.info("AdPlanIndexAwareImpl after delete::{}", planIndexObjectMap);

    }
}

package com.sxzhongf.ad.index.creative;

import com.sxzhongf.ad.index.IIndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CreativeIndexAwareImpl for 创意索引实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Slf4j
@Component
public class CreativeIndexAwareImpl implements IIndexAware<Long, CreativeIndexObject> {

    //正向索引对象
    private static Map<Long, CreativeIndexObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 根据ids获取创意list
     */
    public List<CreativeIndexObject> findAllByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return Collections.emptyList();
        List<CreativeIndexObject> result = new ArrayList<>();

        for (Long id : ids) {
            CreativeIndexObject object = get(id);
            if (null != object)
                result.add(object);
        }

        return result;
    }

    @Override
    public CreativeIndexObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeIndexObject value) {
        log.info("CreativeIndexAwareImpl:: before add :{}", objectMap);
        objectMap.put(key, value);
        log.info("CreativeIndexAwareImpl:: after add :{}", objectMap);
    }

    @Override
    public void update(Long key, CreativeIndexObject value) {
        log.info("CreativeIndexAwareImpl:: before update :{}", objectMap);

        CreativeIndexObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("CreativeIndexAwareImpl:: after update :{}", objectMap);

    }

    @Override
    public void delete(Long key, CreativeIndexObject value) {
        log.info("CreativeIndexAwareImpl:: before add :{}", objectMap);
        objectMap.remove(key);
        log.info("CreativeIndexAwareImpl:: after add :{}", objectMap);
    }
}

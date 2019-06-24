package com.sxzhongf.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * CommonUtils for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
public class CommonUtils {

    /**
     * 如果根据当前key在当前map中查询不到结果，
     * 使用factory创建一个新的对象
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}

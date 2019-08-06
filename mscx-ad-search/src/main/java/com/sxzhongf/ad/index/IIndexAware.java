package com.sxzhongf.ad.index;

/**
 * IIndexAware for 实现广告索引的增删改查
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
public interface IIndexAware<K, V> {

    /**
     * 通过key 获取索引
     */
    V get(K key);

    /**
     * 添加索引
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 更新索引
     */
    void update(K key, V value);

    /**
     * 删除索引
     */
    void delete(K key, V value);
}

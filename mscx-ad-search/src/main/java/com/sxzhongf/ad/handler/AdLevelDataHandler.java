package com.sxzhongf.ad.handler;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.export.table.*;
import com.sxzhongf.ad.common.utils.CommonUtils;
import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.index.IndexDataTableUtils;
import com.sxzhongf.ad.index.adplan.AdPlanIndexAwareImpl;
import com.sxzhongf.ad.index.adplan.AdPlanIndexObject;
import com.sxzhongf.ad.index.adunit.AdUnitIndexAwareImpl;
import com.sxzhongf.ad.index.adunit.AdUnitIndexObject;
import com.sxzhongf.ad.index.creative.CreativeIndexAwareImpl;
import com.sxzhongf.ad.index.creative.CreativeIndexObject;
import com.sxzhongf.ad.index.creative_relation_unit.CreativeRelationUnitIndexAwareImpl;
import com.sxzhongf.ad.index.creative_relation_unit.CreativeRelationUnitIndexObject;
import com.sxzhongf.ad.index.district.UnitDistrictIndexAwareImpl;
import com.sxzhongf.ad.index.hobby.UnitHobbyIndexAwareImpl;
import com.sxzhongf.ad.index.hobby.UnitHobbyIndexObject;
import com.sxzhongf.ad.index.keyword.UnitKeywordIndexAwareImpl;
import com.sxzhongf.ad.index.keyword.UnitKeywordIndexObject;
import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * AdLevelDataHandler for 通用处理索引类
 * 1. 索引之间存在层级划分，也就是相互之间拥有依赖关系的划分
 * 2. 加载全量索引其实是增量索引 "添加"的一种特殊实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Slf4j
public class AdLevelDataHandler {

    /**
     * 实现广告推广计划的第二层级索引实现。
     * （第一级为用户层级，但是用户层级不参与索引，所以从level 2开始）
     * 第二层级的索引是表示 不依赖于其他索引，但是可被其他索引所依赖
     */
    public static void handleLevel2Index(AdPlanTable adPlanTable, OperationTypeEnum type) {
        AdPlanIndexObject planIndexObject = new AdPlanIndexObject(
                adPlanTable.getPlanId(),
                adPlanTable.getUserId(),
                adPlanTable.getPlanStatus(),
                adPlanTable.getStartDate(),
                adPlanTable.getEndDate()
        );

        //调用通用方法处理，使用IndexDataTableUtils#of来获取索引的实现类bean
        handleBinlogEvent(
                // 在前一节我们实现了一个索引工具类，来获取注入的bean对象
                IndexDataTableUtils.of(AdPlanIndexAwareImpl.class),
                planIndexObject.getPlanId(),
                planIndexObject,
                type
        );
    }

    /**
     * 实现广告创意的第二层级索引实现。
     * （第一级为用户层级，但是用户层级不参与索引，所以从level 2开始）
     * 第二层级的索引是表示 不依赖于其他索引，但是可被其他索引所依赖
     */
    public static void handleLevel2Index(AdCreativeTable creativeTable, OperationTypeEnum type) {
        CreativeIndexObject creativeIndexObject = new CreativeIndexObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );

        handleBinlogEvent(
                IndexDataTableUtils.of(CreativeIndexAwareImpl.class),
                creativeIndexObject.getAdId(),
                creativeIndexObject,
                type
        );
    }

    /**
     * 实现广告推广单元的第三层级索引实现。
     * 第三层级的索引是表示 依赖于二级索引
     *
     * @param type 操作类型 {@link OperationTypeEnum}
     */
    public static void handleLevel3Index(AdUnitTable adUnitTable, OperationTypeEnum type) {

        //从索引中获取推广单元对应的推广计划
        AdPlanIndexObject planIndexObject = IndexDataTableUtils.of(
                AdPlanIndexAwareImpl.class
        ).get(adUnitTable.getPlanId());

        if (null == planIndexObject) {
            log.error("当前推广单元对应的推广计划还未生成，请稍后再试。Error:{}", adUnitTable.getPlanId());
            return;
        }

        AdUnitIndexObject unitIndexObject = new AdUnitIndexObject(
                adUnitTable.getUnitId(),
                adUnitTable.getPlanId(),
                adUnitTable.getUnitStatus(),
                adUnitTable.getPositionType(),
                planIndexObject
        );

        //添加索引信息
        handleBinlogEvent(
                IndexDataTableUtils.of(AdUnitIndexAwareImpl.class),
                unitIndexObject.getUnitId(),
                unitIndexObject,
                type
        );
    }

    /**
     * 实现广告创意和推广单元关联关系的第三层级索引实现。
     * 第三层级的索引是表示 依赖于二级索引
     *
     * @param type 操作类型 {@link OperationTypeEnum}
     */
    public static void handleLevel3Index(AdCreativeRelationUnitTable creativeRelationUnitTable, OperationTypeEnum type) {

        //不支持更新操作
        if (type == OperationTypeEnum.UPDATE) {
            log.error("AdCreativeRelationUnitTable not support update.");
            return;
        }

        //判断当前对象对应的推广单元和创意是否存在
        AdUnitIndexObject unitIndexObject = IndexDataTableUtils.of(
                AdUnitIndexAwareImpl.class
        ).get(creativeRelationUnitTable.getUnitId());
        if (null == unitIndexObject) {
            log.error("当前广告创意和推广单元关联关系数据对应的推广单元还未生成，请稍后再试。Error:{}",
                    creativeRelationUnitTable.getUnitId());
            return;
        }

        CreativeIndexObject creativeIndexObject = IndexDataTableUtils.of(
                CreativeIndexAwareImpl.class
        ).get(creativeRelationUnitTable.getAdId());

        if (null == creativeIndexObject) {
            log.error("当前广告创意和推广单元关联关系数据对应的创意还未生成，请稍后再试。Error:{}",
                    creativeRelationUnitTable.getAdId());
            return;
        }

        //转换为需要存储的索引对象
        CreativeRelationUnitIndexObject creativeRelationUnitIndexObject = new CreativeRelationUnitIndexObject(
                creativeRelationUnitTable.getAdId(),
                creativeRelationUnitTable.getUnitId()
        );

        //处理
        handleBinlogEvent(
                IndexDataTableUtils.of(
                        CreativeRelationUnitIndexAwareImpl.class
                ),
                CommonUtils.stringConcat(
                        String.valueOf(creativeRelationUnitTable.getAdId()),
                        String.valueOf(creativeRelationUnitTable.getUnitId())
                ),
                creativeRelationUnitIndexObject,
                type
        );

    }


    /**
     * 实现推广单元 地域维度 的第四层级索引实现。
     * 第4层级的索引是表示 依赖于3级索引
     *
     * @param type 操作类型 {@link OperationTypeEnum}
     */
    public static void handleLevel4Index(AdUnitDistrictTable unitDistrictTable, OperationTypeEnum type) {

        //不支持更新操作
        if (type == OperationTypeEnum.UPDATE) {
            log.error("AdUnitDistrictTable does not support update.");
            return;
        }
        AdUnitIndexObject unitIndexObject = IndexDataTableUtils.of(AdUnitIndexAwareImpl.class).get(unitDistrictTable.getUnitId());
        if (null == unitIndexObject) {
            log.error("当前地域索引对应的推广单元索引还未生成，Error:{}", unitDistrictTable.getUnitId());
            return;
        }

        //构建索引的key & value
        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );

        handleBinlogEvent(
                IndexDataTableUtils.of(UnitDistrictIndexAwareImpl.class),
                key,
                value,
                type
        );
    }

    /**
     * 实现推广单元 关键词维度 的第四层级索引实现。
     * 第4层级的索引是表示 依赖于3级索引
     *
     * @param type 操作类型 {@link OperationTypeEnum}
     */
    public static void handleLevel4Index(AdUnitKeywordTable unitKeywordTable, OperationTypeEnum type) {
        //不支持更新操作
        if (type == OperationTypeEnum.UPDATE) {
            log.error("AdUnitKeywordTable does not support update.");
            return;
        }

        //在索引中查询对应的推广单元
        AdUnitIndexObject unitIndexObject = IndexDataTableUtils.of(AdUnitIndexAwareImpl.class).get(unitKeywordTable.getUnitId());
        if (null == unitIndexObject) {
            log.error("当前关键词索引对应的推广单元索引还未生成，Error:{}", unitKeywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(unitIndexObject.getUnitId())
        );
        handleBinlogEvent(
                IndexDataTableUtils.of(UnitKeywordIndexAwareImpl.class),
                unitKeywordTable.getKeyword(),
                value,
                type
        );
    }

    /**
     * 实现推广单元 兴趣维度 的第四层级索引实现。
     * 第4层级的索引是表示 依赖于3级索引
     *
     * @param type 操作类型 {@link OperationTypeEnum}
     */
    public static void handleLevel4Index(AdUnitHobbyTable unitHobbyTable, OperationTypeEnum type) {

        //判断是否为更新操作
        if (type == OperationTypeEnum.UPDATE) {
            log.error("AdUnitHobbyTable does not support update.");
            return;
        }

        //检查当前推广计划是否存在索引中
        AdUnitIndexObject unitIndexObject = IndexDataTableUtils.of(AdUnitIndexAwareImpl.class).get(unitHobbyTable.getUnitId());
        if (null == unitIndexObject) {
            log.error("当前兴趣索引对应的推广单元索引还未生成，Error:{}", unitHobbyTable.getUnitId());
            return;
        }

        //设置兴趣索引的key & value
        String key = unitHobbyTable.getHobbyTag();
        Set<Long> value = new HashSet<>(Collections.singleton(unitIndexObject.getUnitId()));

        handleBinlogEvent(
                IndexDataTableUtils.of(UnitHobbyIndexAwareImpl.class),
                key,
                value,
                type
        );
    }


    /**
     * 处理全量索引和增量索引的通用处理方式
     * K,V代表索引的键和值
     *
     * @param index 索引实现代理类父级
     * @param key   键
     * @param value 值
     * @param type  操作类型
     */
    private static <K, V> void handleBinlogEvent(IIndexAware<K, V> index, K key, V value, OperationTypeEnum type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}

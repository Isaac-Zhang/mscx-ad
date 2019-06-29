package com.sxzhongf.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.export.table.*;
import com.sxzhongf.ad.common.utils.CommonUtils;
import com.sxzhongf.ad.handler.AdLevelDataHandler;
import com.sxzhongf.ad.index.AdDataLevel;
import com.sxzhongf.ad.mysql.constant.Constant;
import com.sxzhongf.ad.mysql.dto.MysqlRowData;
import com.sxzhongf.ad.sender.ISender;
import javafx.collections.transformation.SortedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * IndexSender for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/28
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    /**
     * 根据广告级别，投递Binlog数据
     */
    @Override
    public void sender(MysqlRowData rowData) {
        if (AdDataLevel.LEVEL2.getLevel().equals(rowData.getLevel())) {
            Level2RowData(rowData);
        } else if (AdDataLevel.LEVEL3.getLevel().equals(rowData.getLevel())) {
            Level3RowData(rowData);
        } else if (AdDataLevel.LEVEL4.getLevel().equals(rowData.getLevel())) {
            Level4RowData(rowData);
        } else {
            log.error("Binlog MysqlRowData error: {}", JSON.toJSONString(rowData));
        }
    }

    private void Level2RowData(MysqlRowData rowData) {

        if (rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> planTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();

                //Map的第一种循环方式
//                for (Map.Entry<String, String> stringEntry : fieldValueMap.entrySet()) {
//                    switch (stringEntry.getKey()) {
//                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_ID:
//                            planTable.setPlanId(Long.valueOf(stringEntry.getValue()));
//                            break;
//                    }
//                }

                //Map的第二种循环方式
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_ID:
                            planTable.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseBinlogString2Date(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseBinlogString2Date(v));
                            break;
                    }
                });
                planTables.add(planTable);
            }

            //投递推广计划
            planTables.forEach(p -> AdLevelDataHandler.handleLevel2Index(p, rowData.getOperationTypeEnum()));
        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            List<AdCreativeTable> creativeTables = new LinkedList<>();

            rowData.getFieldValueMap().forEach(afterMap -> {
                AdCreativeTable creativeTable = new AdCreativeTable();
                afterMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            });

            //投递广告创意
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2Index(c, rowData.getOperationTypeEnum()));
        }
    }

    private void Level3RowData(MysqlRowData rowData) {
        // 1.判断要投递的数据表名
        if (rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)) {
            // 2.创建要处理的对象列表
            List<AdUnitTable> unitTables = new LinkedList<>();

            /**
             * 3.获取是否有变更后的数据，
             * 即{@link BinlogRowData} 中的 after
             */
            rowData.getFieldValueMap().forEach(afterMap -> {
                // 4.循环afterMap 数据
                afterMap.forEach((k, v) -> {
                    // 5.创建要处理的对象
                    AdUnitTable unitTable = new AdUnitTable();
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUNN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUNN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                    }
                    // 6.添加到列表中
                    unitTables.add(unitTable);
                });
            });

            // 7.投递数据到索引中
            unitTables.forEach(u -> AdLevelDataHandler.handleLevel3Index(u, rowData.getOperationTypeEnum()));

        } else if (rowData.getTableName().equals(Constant.RELATIONSHIP_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<AdCreativeRelationUnitTable> creativeRelationUnitTables = new ArrayList<>();

            for (Map<String, String> afterMap : rowData.getFieldValueMap()) {
                afterMap.forEach((k, v) -> {
                    AdCreativeRelationUnitTable creativeRelationUnitTable = new AdCreativeRelationUnitTable();
                    switch (k) {
                        case Constant.RELATIONSHIP_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeRelationUnitTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.RELATIONSHIP_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeRelationUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                    creativeRelationUnitTables.add(creativeRelationUnitTable);
                });
            }

            creativeRelationUnitTables.forEach(cu -> AdLevelDataHandler.handleLevel3Index(cu, rowData.getOperationTypeEnum()));
        }
    }

    /**
     * 处理4级广告
     */
    private void Level4RowData(MysqlRowData rowData) {
        if (rowData.getTableName().equals(Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME)) {
            List<AdUnitKeywordTable> keywordTables = new LinkedList<>();
            for (Map<String, String> afterMap : rowData.getFieldValueMap()) {
                afterMap.forEach((k, v) -> {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();
                    switch (k) {
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                            keywordTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                            keywordTable.setKeyword(v);
                            break;
                    }
                    keywordTables.add(keywordTable);
                });
            }

            keywordTables.forEach(k -> AdLevelDataHandler.handleLevel4Index(k, rowData.getOperationTypeEnum()));
        } else if (rowData.getTableName().equals(Constant.AD_UNIT_HOBBY_TABLE_INFO.TABLE_NAME)) {
            List<AdUnitHobbyTable> hobbyTables = new LinkedList<>();
            for (Map<String, String> afterMap : rowData.getFieldValueMap()) {
                afterMap.forEach((k, v) -> {
                    AdUnitHobbyTable hobbyTable = new AdUnitHobbyTable();
                    switch (k) {
                        case Constant.AD_UNIT_HOBBY_TABLE_INFO.COLUMN_UNIT_ID:
                            hobbyTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_HOBBY_TABLE_INFO.COLUMN_HOBBY_TAG:
                            hobbyTable.setHobbyTag(v);
                            break;
                    }
                    hobbyTables.add(hobbyTable);
                });
            }

            hobbyTables.forEach(h -> AdLevelDataHandler.handleLevel4Index(h, rowData.getOperationTypeEnum()));
        } else if (rowData.getTableName().equals(Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME)) {
            List<AdUnitDistrictTable> districtTables = new LinkedList<>();
            for (Map<String, String> afterMap : rowData.getFieldValueMap()) {
                afterMap.forEach((k, v) -> {
                    AdUnitDistrictTable districtTable = new AdUnitDistrictTable();
                    switch (k) {
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                            districtTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                            districtTable.setProvince(v);
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                            districtTable.setCity(v);
                            break;
                    }
                    districtTables.add(districtTable);
                });
            }

            districtTables.forEach(d -> AdLevelDataHandler.handleLevel4Index(d, rowData.getOperationTypeEnum()));
        }
    }
}

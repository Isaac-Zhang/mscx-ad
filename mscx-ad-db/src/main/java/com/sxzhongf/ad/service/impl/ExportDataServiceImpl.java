package com.sxzhongf.ad.service.impl;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.export.table.*;
import com.sxzhongf.ad.constant.CommonStatus;
import com.sxzhongf.ad.dao.AdPlanRepository;
import com.sxzhongf.ad.dao.AdUnitRepository;
import com.sxzhongf.ad.dao.CreativeRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitHobbyRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.sxzhongf.ad.dao.unit_condition.RelationshipCreativeUnitRepository;
import com.sxzhongf.ad.entity.AdCreative;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.entity.AdUnit;
import com.sxzhongf.ad.entity.unit_condition.AdUnitDistrict;
import com.sxzhongf.ad.entity.unit_condition.AdUnitHobby;
import com.sxzhongf.ad.entity.unit_condition.AdUnitKeyword;
import com.sxzhongf.ad.entity.unit_condition.RelationshipCreativeUnit;
import com.sxzhongf.ad.service.IExportDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * ExportDataServiceImpl for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Slf4j
@Service
public class ExportDataServiceImpl implements IExportDataService {

    @Autowired
    private AdPlanRepository planRepository;

    @Autowired
    private AdUnitRepository unitRepository;

    @Autowired
    private AdUnitDistrictRepository districtRepository;

    @Autowired
    private AdUnitHobbyRepository hobbyRepository;

    @Autowired
    private AdUnitKeywordRepository keywordRepository;

    @Autowired
    private CreativeRepository creativeRepository;

    @Autowired
    private RelationshipCreativeUnitRepository relationshipCreativeUnitRepository;

    /**
     * 导出 {@code AdPlan} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdPlanTable(String fileName) {
        List<AdPlan> planList = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(planList)) {
            return;
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        planList.forEach(item -> planTables.add(
                new AdPlanTable(
                        item.getPlanId(),
                        item.getUserId(),
                        item.getPlanStatus(),
                        item.getStartDate(),
                        item.getEndDate()
                )
        ));

        //将数据写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable adPlanTable : planTables) {
                writer.write(JSON.toJSONString(adPlanTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export AdPlanTable Exception!");
        }
    }

    /**
     * 导出 {@code AdUnit} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdUnitTable(String fileName) {
        List<AdUnit> unitList = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(unitList)) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        unitList.forEach(item -> unitTables.add(
                new AdUnitTable(
                        item.getUnitId(),
                        item.getPlanId(),
                        item.getUnitStatus(),
                        item.getPositionType()
                )
        ));

        //将数据写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable adUnitTable : unitTables) {
                writer.write(JSON.toJSONString(adUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export adUnitTable Exception!");
        }
    }

    /**
     * 导出 {@code AdCreative} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdCreativeTable(String fileName) {
        List<AdCreative> creativeList = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creativeList)) {
            return;
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creativeList.forEach(item -> creativeTables.add(
                new AdCreativeTable(
                        item.getCreativeId(),
                        item.getName(),
                        item.getType(),
                        item.getMaterialType(),
                        item.getHeight(),
                        item.getWidth(),
                        item.getAuditStatus(),
                        item.getUrl()
                )
        ));

        //将数据写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable adCreativeTable : creativeTables) {
                writer.write(JSON.toJSONString(adCreativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export adCreativeTable Exception!");
        }
    }


    /**
     * 导出 {@code AdCreativeRelationUnit} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdCreativeRelationUnit(String fileName) {
        List<RelationshipCreativeUnit> relationshipCreativeUnitList = relationshipCreativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(relationshipCreativeUnitList)) {
            return;
        }

        List<AdCreativeRelationUnitTable> creativeRelationUnitTables = new ArrayList<>();
        relationshipCreativeUnitList
                .forEach(item -> creativeRelationUnitTables.add(new AdCreativeRelationUnitTable(
                                item.getCreativeId(),
                                item.getUnitId()
                        ))
                );

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeRelationUnitTable item : creativeRelationUnitTables) {
                writer.write(JSON.toJSONString(item));
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("export AdCreativeRelationUnitTable Exception!");
        }
    }

    /**
     * 导出 {@code AdUnitHobby} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdUnitHobby(String fileName) {
        List<AdUnitHobby> unitHobbyList = hobbyRepository.findAll();
        if (CollectionUtils.isEmpty(unitHobbyList)) {
            return;
        }

        List<AdUnitHobbyTable> unitHobbyTables = new ArrayList<>();
        unitHobbyList.forEach(item -> unitHobbyTables.add(
                new AdUnitHobbyTable(
                        item.getUnitId(),
                        item.getHobbyTag()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitHobbyTable unitHobbyTable : unitHobbyTables) {
                writer.write(JSON.toJSONString(unitHobbyTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.getStackTrace();
            log.error("export AdUnitHobbyTable Exception.");
        }
    }

    /**
     * 导出 {@code AdUnitKeywords} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdUnitKeywords(String fileName) {
        List<AdUnitKeyword> unitKeywordList = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywordList)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywordList.forEach(item -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        item.getUnitId(),
                        item.getKeyword()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable item : unitKeywordTables) {
                writer.write(JSON.toJSONString(item));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export AdUnitKeywordTable Exception.");
        }
    }

    /**
     * 导出 {@code AdUnitDistrict} from DB to File
     *
     * @param fileName 文件名称
     */
    public void exportAdUnitDistrict(String fileName) {
        //获取数据表中的数据
        List<AdUnitDistrict> unitDistrictList = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistrictList)) {
            return;
        }

        //将数据转换为需要存储的对象集合
        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistrictList.forEach(item -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        item.getUnitId(),
                        item.getProvince(),
                        item.getCity()
                )
        ));

        //根据路径和文件名称获取path
        Path path = Paths.get(fileName);

        //使用BufferedWriter 写入文件
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            //循环写入文件
            for (AdUnitDistrictTable item : unitDistrictTables) {
                writer.write(JSON.toJSONString(item));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export AdUnitDistrictTable Exception.");
        }
    }
}

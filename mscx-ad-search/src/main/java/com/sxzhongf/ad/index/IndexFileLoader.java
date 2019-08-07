package com.sxzhongf.ad.index;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.export.DConstant;
import com.sxzhongf.ad.common.export.table.*;
import com.sxzhongf.ad.handler.AdLevelDataHandler;
import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IndexFileLoader for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Component
@DependsOn("indexDataTableUtils")
public class IndexFileLoader {

    /**
     * 服务启动时，执行全量索引加载
     */
    @PostConstruct
    public void init() {
        //加载 推广计划
        List<String> adPlanStrings = loadExportedData(String.format("%s%s",
                DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN
        ));
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2Index(
                JSON.parseObject(p, AdPlanTable.class), OperationTypeEnum.ADD
        ));

        //加载广告创意
        List<String> adCreativeStrings = loadExportedData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE
                )
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2Index(
                JSON.parseObject(c, AdCreativeTable.class), OperationTypeEnum.ADD
        ));

        //加载推广单元
        List<String> adUnitStrings = loadExportedData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3Index(
                JSON.parseObject(u, AdUnitTable.class), OperationTypeEnum.ADD
        ));

        //加载推广单元和广告创意的关联关系
        List<String> creativeRelationUnitStrings = loadExportedData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_RELARION_UNIT)
        );
        creativeRelationUnitStrings.forEach(r -> AdLevelDataHandler.handleLevel3Index(
                JSON.parseObject(r, AdCreativeRelationUnitTable.class), OperationTypeEnum.ADD
        ));

        //加载推广单元下的地域限制索引
        List<String> unitDistrictStrings = loadExportedData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
        unitDistrictStrings.forEach(d -> AdLevelDataHandler.handleLevel4Index(
                JSON.parseObject(d, AdUnitDistrictTable.class), OperationTypeEnum.ADD
        ));

        //加载推广单元下的关键词限制索引
        List<String> unitKeywordStrings = loadExportedData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );
        unitKeywordStrings.forEach(k -> AdLevelDataHandler.handleLevel4Index(
                JSON.parseObject(k, AdUnitKeywordTable.class), OperationTypeEnum.ADD
        ));

        //加载推广单元下的兴趣限制索引
        List<String> unitHobbyStrings = loadExportedData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_HOBBY)
        );
        unitHobbyStrings.forEach(h -> AdLevelDataHandler.handleLevel4Index(
                JSON.parseObject(h, AdUnitHobbyTable.class), OperationTypeEnum.ADD
        ));
    }

    /**
     * <h3>读取全量索引加载需要的文件</h3>
     *
     * @param fileName 文件名称
     * @return 文件行数据
     */
    private List<String> loadExportedData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

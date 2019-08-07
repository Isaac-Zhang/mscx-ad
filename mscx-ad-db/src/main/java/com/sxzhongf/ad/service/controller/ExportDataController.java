package com.sxzhongf.ad.service.controller;

import com.sxzhongf.ad.common.export.DConstant;
import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.service.impl.ExportDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ExportDataController for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Slf4j
@Controller
@RequestMapping("/export")
public class ExportDataController {

    private final ExportDataServiceImpl exportDataService;


    @Autowired
    public ExportDataController(ExportDataServiceImpl exportDataService) {
        this.exportDataService = exportDataService;
    }

    @GetMapping("/plan")
    public CommonResponse exportAdPlans() {

        exportDataService.exportAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        exportDataService.exportAdCreativeRelationUnit(String.format("%s%s"
                , DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_RELARION_UNIT));
        exportDataService.exportAdCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        exportDataService.exportAdUnitDistrict(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        exportDataService.exportAdUnitHobby(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_HOBBY));
        exportDataService.exportAdUnitKeywords(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        exportDataService.exportAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        return new CommonResponse();
    }
}

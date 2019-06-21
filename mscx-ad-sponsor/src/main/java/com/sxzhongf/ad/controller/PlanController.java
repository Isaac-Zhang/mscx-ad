package com.sxzhongf.ad.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.annotation.IgnoreResponseAdvice;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.service.IPlanService;
import com.sxzhongf.ad.vo.PlanGetRequestVO;
import com.sxzhongf.ad.vo.PlanRequestVO;
import com.sxzhongf.ad.vo.PlanResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PlanController for 推广计划controller
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@RestController
@Slf4j
@RequestMapping(path = "/plan")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @IgnoreResponseAdvice
    @GetMapping(path = "/get")
    public CommonResponse<List<AdPlan>> getAdPlanByPlanIds(@RequestBody PlanGetRequestVO requestVO) throws AdException {
        log.info("Ad-sponsor: getAdPlanByPlanIds->{}", JSON.toJSONString(requestVO));
        List<AdPlan> planList = planService.getAdPlanByPlanIds(requestVO);
        log.info("Ad-sponsor: getAdPlanByPlanIds result->{}", JSON.toJSONString(planList));

        return new CommonResponse<>(planList);
    }

    @PostMapping(value = "/create")
    public PlanResponseVO createPlan(@RequestBody PlanRequestVO requestVO) throws AdException {
        log.info("Ad-sponsor: createPlan->{}", JSON.toJSONString(requestVO));
        return planService.createPlan(requestVO);
    }

    @PostMapping(value = "/update")
    public PlanResponseVO updatePlan(@RequestBody PlanRequestVO requestVO) throws AdException {
        log.info("Ad-sponsor: updatePlan->{}", JSON.toJSONString(requestVO));
        return planService.updatePlan(requestVO);
    }

    @IgnoreResponseAdvice
    @PostMapping(value = "/delete")
    public CommonResponse deletePlan(@RequestBody PlanRequestVO requestVO) throws AdException {
        log.info("Ad-sponsor: deletePlan->{}", JSON.toJSONString(requestVO));
        planService.deletePlan(requestVO);
        return new CommonResponse();
    }

}

package com.sxzhongf.ad.service;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.vo.PlanGetRequestVO;
import com.sxzhongf.ad.vo.PlanRequestVO;
import com.sxzhongf.ad.vo.PlanResponseVO;

import java.util.List;

/**
 * IPlanService for 推广计划service 接口
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
public interface IPlanService {

    /**
     * 创建推广计划
     */
    PlanResponseVO createPlan(PlanRequestVO planRequestVO) throws AdException;

    /**
     * 获取推广计划
     */
    List<AdPlan> getAdPlanByPlanIds(PlanGetRequestVO planGetRequestVO) throws AdException;

    /**
     * 更新推广计划
     */
    PlanResponseVO updatePlan(PlanRequestVO planRequestVO) throws AdException;

    /**
     * 删除推广计划
     */
    void deletePlan(PlanRequestVO planRequestVO) throws AdException;
}

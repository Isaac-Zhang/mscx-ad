package com.sxzhongf.ad.service.impl;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.utils.CommonUtils;
import com.sxzhongf.ad.constant.CommonStatus;
import com.sxzhongf.ad.constant.Constants;
import com.sxzhongf.ad.dao.AdPlanRepository;
import com.sxzhongf.ad.dao.AdUserRepository;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.entity.AdUser;
import com.sxzhongf.ad.service.IPlanService;
import com.sxzhongf.ad.vo.PlanGetRequestVO;
import com.sxzhongf.ad.vo.PlanRequestVO;
import com.sxzhongf.ad.vo.PlanResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * PlanServiceImpl for 推广单元service
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Slf4j
@Service
public class PlanServiceImpl implements IPlanService {

    private final AdUserRepository userRepository;

    private AdPlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public PlanResponseVO createPlan(PlanRequestVO planRequestVO) throws AdException {

        if (!planRequestVO.createValidate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }

        //确保关联的User是存在的
        Optional<AdUser> user = userRepository.findById(planRequestVO.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMessage.USER_NOT_EXIST);
        }

        AdPlan plan = planRepository.findByUserIdAndPlanName(planRequestVO.getUserId(), planRequestVO.getPlanName());
        if (null != plan) {
            throw new AdException("已存在相同名称的推广计划！");
        }

        AdPlan insertedPlan = planRepository.save(
                new AdPlan(
                        planRequestVO.getUserId(),
                        planRequestVO.getPlanName(),
                        CommonUtils.parseStringDate(planRequestVO.getStartDate()),
                        CommonUtils.parseStringDate(planRequestVO.getEndDate()))
        );
        return new PlanResponseVO(insertedPlan.getPlanId(), insertedPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByPlanIds(PlanGetRequestVO planGetRequestVO) throws AdException {
        if (!planGetRequestVO.validate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<AdPlan> planList = planRepository.findAllByPlanIdsAndUserId(planGetRequestVO.getPlanIds()
                , planGetRequestVO.getUserId());
        return planList;
    }

    @Override
    @Transactional
    public PlanResponseVO updatePlan(PlanRequestVO planRequestVO) throws AdException {
        if (!planRequestVO.updateOrDeleteValidate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }

        AdPlan oldPlan = planRepository.findByPlanIdAndUserId(planRequestVO.getPlanId(), planRequestVO.getUserId());
        if (null != oldPlan) {
            throw new AdException("找不到当前推广计划，更新失败！");
        }

        if (null != planRequestVO.getPlanName()) {
            oldPlan.setPlanName(planRequestVO.getPlanName());
        }
        if (null != planRequestVO.getStartDate()) {
            oldPlan.setStartDate(CommonUtils.parseStringDate(planRequestVO.getStartDate()));
        }
        if (null != planRequestVO.getEndDate()) {
            oldPlan.setEndDate(CommonUtils.parseStringDate(planRequestVO.getEndDate()));
        }
        oldPlan.setUpdateTime(new Date());

        AdPlan plan = planRepository.save(oldPlan);
        return new PlanResponseVO(plan.getPlanId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deletePlan(PlanRequestVO planRequestVO) throws AdException {
        if (!planRequestVO.updateOrDeleteValidate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> oldPlan = planRepository.findById(planRequestVO.getPlanId());
        if (!oldPlan.isPresent()) {
            throw new AdException("找不到当前推广计划，删除失败！");
        }

        AdPlan plan = oldPlan.get();
        //虚拟删除
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);

//        planRepository.deleteById(planRequestVO.getPlanId());
    }
}

package com.sxzhongf.ad.service.impl;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.constant.Constants;
import com.sxzhongf.ad.dao.AdPlanRepository;
import com.sxzhongf.ad.dao.AdUnitRepositoy;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.entity.AdUnit;
import com.sxzhongf.ad.service.IUnitService;
import com.sxzhongf.ad.vo.UnitRequestVO;
import com.sxzhongf.ad.vo.UnitResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UnitServiceImpl for 推广单元service
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Slf4j
@Service
public class UnitServiceImpl implements IUnitService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepositoy unitRepositoy;

    @Autowired
    public UnitServiceImpl(AdPlanRepository planRepository, AdUnitRepositoy unitRepositoy) {
        this.planRepository = planRepository;
        this.unitRepositoy = unitRepositoy;
    }

    /**
     * 创建推广单元
     */
    @Override
    public UnitResponseVO createUnit(UnitRequestVO unitRequestVO) throws AdException {

        if (!unitRequestVO.creteValidate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> plan = planRepository.findById(unitRequestVO.getPlanId());
        if (!plan.isPresent()) {
            throw new AdException("推广计划不存在！");
        }

        AdUnit oldUnit = unitRepositoy.findByPlanIdAndUnitName(unitRequestVO.getPlanId(), unitRequestVO.getUnitName());
        if(null != oldUnit){
            throw new AdException("已经存在同名称的推广单元！");
        }

        AdUnit unit = unitRepositoy.save(new AdUnit(
                unitRequestVO.getPlanId(),
                unitRequestVO.getUnitName(),
                unitRequestVO.getPositionType(),
                unitRequestVO.getBudgetFee()
        ));

        return new UnitResponseVO(unit.getUnitId(), unit.getUnitName());
    }
}

package com.sxzhongf.ad.service.impl;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.constant.Constants;
import com.sxzhongf.ad.dao.AdPlanRepository;
import com.sxzhongf.ad.dao.AdUnitRepository;
import com.sxzhongf.ad.dao.CreativeRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitHobbyRepository;
import com.sxzhongf.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.sxzhongf.ad.dao.unit_condition.RelationshipCreativeUnitRepository;
import com.sxzhongf.ad.entity.AdPlan;
import com.sxzhongf.ad.entity.AdUnit;
import com.sxzhongf.ad.entity.unit_condition.AdUnitDistrict;
import com.sxzhongf.ad.entity.unit_condition.AdUnitHobby;
import com.sxzhongf.ad.entity.unit_condition.AdUnitKeyword;
import com.sxzhongf.ad.entity.unit_condition.RelationshipCreativeUnit;
import com.sxzhongf.ad.service.IUnitService;
import com.sxzhongf.ad.client.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    private final AdUnitRepository unitRepository;
    private final CreativeRepository creativeRepository;
    private final RelationshipCreativeUnitRepository relationshipCreativeUnitRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitHobbyRepository unitHobbyRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;

    @Autowired
    public UnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository
            , CreativeRepository creativeRepository
            , RelationshipCreativeUnitRepository relationshipCreativeUnitRepository
            , AdUnitKeywordRepository unitKeywordRepository, AdUnitHobbyRepository unitHobbyRepository
            , AdUnitDistrictRepository unitDistrictRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.creativeRepository = creativeRepository;
        this.relationshipCreativeUnitRepository = relationshipCreativeUnitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitHobbyRepository = unitHobbyRepository;
        this.unitDistrictRepository = unitDistrictRepository;
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

        AdUnit oldUnit = unitRepository.findByPlanIdAndUnitName(unitRequestVO.getPlanId(), unitRequestVO.getUnitName());
        if (null != oldUnit) {
            throw new AdException("已经存在同名称的推广单元！");
        }

        AdUnit unit = unitRepository.save(new AdUnit(
                unitRequestVO.getPlanId(),
                unitRequestVO.getUnitName(),
                unitRequestVO.getPositionType(),
                unitRequestVO.getBudgetFee()
        ));

        return new UnitResponseVO(unit.getUnitId(), unit.getUnitName());
    }

    @Override
    @Transactional
    public UnitKeywordResponseVO createUnitKeyword(UnitKeywordRequestVO requestVO) throws AdException {
        List<Long> unitIds = requestVO.getUnitKeywordList()
                                      .stream()
                                      .map(UnitKeywordRequestVO.UnitKeyword::getUnitId)
                                      .collect(Collectors.toList()
                                      );
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<AdUnitKeyword> unitKeywordList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(requestVO.getUnitKeywordList())) {
            //循环添加要插入的 关键词对象到 List
            requestVO.getUnitKeywordList().forEach(item -> unitKeywordList.add(
                    new AdUnitKeyword(item.getUnitId(), item.getKeyword())
            ));
        }

        List<AdUnitKeyword> newUnitKeywordList = unitKeywordRepository.saveAll(unitKeywordList);
        List<Long> ids = newUnitKeywordList.stream()
                                           .map(AdUnitKeyword::getKeywordId)
                                           .collect(Collectors.toList());

        return new UnitKeywordResponseVO(ids);
    }

    @Override
    public UnitHobbyResponseVO createUnitHobby(UnitHobbyRequestVO requestVO) throws AdException {
        List<Long> unitIds = requestVO.getUnitHobbyList()
                                      .stream()
                                      .map(UnitHobbyRequestVO.UnitHobby::getUnitId)
                                      .collect(Collectors.toList()
                                      );
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.EMPTY_LIST;
        List<AdUnitHobby> unitHobbyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(requestVO.getUnitHobbyList())) {
            //循环添加要插入的 关键词对象到 List
            requestVO.getUnitHobbyList().forEach(item -> unitHobbyList.add(
                    new AdUnitHobby(item.getUnitId(), item.getHobbyTag())
            ));
        }

        List<AdUnitHobby> newUnitHobbyLisy = unitHobbyRepository.saveAll(unitHobbyList);
        ids = newUnitHobbyLisy.stream()
                              .map(AdUnitHobby::getHobbyId)
                              .collect(Collectors.toList());

        return new UnitHobbyResponseVO(ids);
    }

    @Override
    public UnitDistrictResponseVO createUnitDistrict(UnitDistrictRequestVO requestVO) throws AdException {
        List<Long> unitIds = requestVO.getUnitDistrictList()
                                      .stream()
                                      .map(UnitDistrictRequestVO.UnitDistrict::getUnitId)
                                      .collect(Collectors.toList()
                                      );
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.EMPTY_LIST;
        List<AdUnitDistrict> unitDistrictList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(requestVO.getUnitDistrictList())) {
            //循环添加要插入的 关键词对象到 List
            requestVO.getUnitDistrictList().forEach(item -> unitDistrictList.add(
                    new AdUnitDistrict(item.getUnitId(), item.getProvince(), item.getCity())
            ));
        }

        List<AdUnitDistrict> newUnitDistrictList = unitDistrictRepository.saveAll(unitDistrictList);
        ids = unitDistrictList.stream()
                              .map(AdUnitDistrict::getDistrictId)
                              .collect(Collectors.toList());

        return new UnitDistrictResponseVO(ids);
    }

    @Override
    public CreativeUnitRelationshipResponseVO createCreativeUnitRelationship(CreativeUnitRelationshipRequestVO requestVO) throws AdException {

        //获取creativeIds & unitIds
        List<Long> creativeIds = requestVO.getCreativeUnitItemVO()
                                          .stream()
                                          .map(CreativeUnitRelationshipRequestVO.CreativeUnitItemVO::getCreativeId)
                                          .collect(Collectors.toList());
        List<Long> unitIds = requestVO.getCreativeUnitItemVO()
                                      .stream()
                                      .map(CreativeUnitRelationshipRequestVO.CreativeUnitItemVO::getUnitId)
                                      .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds) && !isRelatedCreativeExist(creativeIds)) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }

        //循环组建要插入的对象list
        List<RelationshipCreativeUnit> entityList = new ArrayList<>();
        requestVO.getCreativeUnitItemVO().stream()
                 //.map(item -> entityList.add(new RelationshipCreativeUnit(item.getUnitId(), item.getCreativeId())))
                 .map(item -> {
                     return entityList.add(new RelationshipCreativeUnit(item.getUnitId(), item.getCreativeId()));
                 })
                 .collect(Collectors.toList());
        List<Long> ids = relationshipCreativeUnitRepository.saveAll(entityList)
                                                           .stream()
                                                           .map(RelationshipCreativeUnit::getId)
                                                           .collect(Collectors.toList());
        return new CreativeUnitRelationshipResponseVO(ids);
    }

    private boolean isRelatedUnitExist(Collection<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        //因为hashset在赋值的时候，相同值（引用）不会添加，相当于实现自动去重
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        //因为hashset在赋值的时候，相同值（引用）不会添加，相当于实现自动去重
        return creativeRepository.findAllById(ids).size() == new HashSet<>(ids).size();
    }
}

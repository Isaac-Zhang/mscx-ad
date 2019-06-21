package com.sxzhongf.ad.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.service.IUnitService;
import com.sxzhongf.ad.service.IUserService;
import com.sxzhongf.ad.client.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UnitController for 推广单元controller
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@Slf4j
@RestController
@RequestMapping(path = "/unit")
public class UnitController {

    private final IUnitService unitService;

    @Autowired
    public UnitController(IUnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping(path = "/create")
    public UnitResponseVO createUnit(@RequestBody UnitRequestVO requestVO) throws AdException {
        log.info("Ad-sponsor::UnitController createUnit->{}", JSON.toJSONString(requestVO));

        return unitService.createUnit(requestVO);
    }

    /**
     * 创建推广单元-关键字
     */
    @PostMapping(value = "/keyword/create")
    public UnitKeywordResponseVO createUnitKeyword(@RequestBody UnitKeywordRequestVO requestVO) throws AdException {

        log.info("Ad-sponsor::UnitController createUnitKeyword->{}", JSON.toJSONString(requestVO));
        return unitService.createUnitKeyword(requestVO);
    }

    /**
     * 创建推广单元-爱好
     */
    @PostMapping(path = "/hobby/create")
    public UnitHobbyResponseVO createUnitHobby(@RequestBody UnitHobbyRequestVO requestVO) throws AdException {

        log.info("Ad-sponsor::UnitController createUnitHobby->{}", JSON.toJSONString(requestVO));
        return unitService.createUnitHobby(requestVO);
    }

    /**
     * 创建推广单元-地域
     */
    @PostMapping(path = "/district/create")
    public UnitDistrictResponseVO createUnitDistrict(@RequestBody UnitDistrictRequestVO requestVO) throws AdException {

        log.info("Ad-sponsor::UnitController createUnitDistrict->{}", JSON.toJSONString(requestVO));
        return unitService.createUnitDistrict(requestVO);
    }

    /**
     * 插入创意和推广单元关系表数据
     */
    @PostMapping("/creative-unit/create")
    public CreativeUnitRelationshipResponseVO createCreativeUnitRelationship(CreativeUnitRelationshipRequestVO requestVO)
            throws AdException {

        log.info("Ad-sponsor::UnitController createCreativeUnitRelationship->{}", JSON.toJSONString(requestVO));
        return unitService.createCreativeUnitRelationship(requestVO);
    }

}

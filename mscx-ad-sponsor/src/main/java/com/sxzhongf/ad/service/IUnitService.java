package com.sxzhongf.ad.service;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.vo.*;

/**
 * IUnitService for 推广单元service
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
public interface IUnitService {
    /**
     * 创建推广单元
     */
    UnitResponseVO createUnit(UnitRequestVO unitRequestVO) throws AdException;

    /**
     * 创建推广单元-关键字
     */
    UnitKeywordResponseVO createUnitKeyword(UnitKeywordRequestVO requestVO) throws AdException;

    /**
     * 创建推广单元-爱好
     */
    UnitHobbyResponseVO createUnitHobby(UnitHobbyRequestVO requestVO) throws AdException;

    /**
     * 创建推广单元-地域
     */
    UnitDistrictResponseVO createUnitDistrict(UnitDistrictRequestVO requestVO) throws AdException;
}

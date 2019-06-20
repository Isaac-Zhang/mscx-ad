package com.sxzhongf.ad.service;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.vo.UnitRequestVO;
import com.sxzhongf.ad.vo.UnitResponseVO;

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
    public UnitResponseVO createUnit(UnitRequestVO unitRequestVO) throws AdException;
}

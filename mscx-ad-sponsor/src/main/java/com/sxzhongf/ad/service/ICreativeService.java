package com.sxzhongf.ad.service;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.client.vo.CreativeRequestVO;
import com.sxzhongf.ad.client.vo.CreativeResponseVO;

/**
 * ICreativeService for 创意service interface
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
public interface ICreativeService {
    /**
     * 新增创意
     */
    CreativeResponseVO createCreative(CreativeRequestVO requestVO) throws AdException;
}

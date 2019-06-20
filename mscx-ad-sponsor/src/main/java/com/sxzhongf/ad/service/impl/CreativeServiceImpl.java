package com.sxzhongf.ad.service.impl;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.dao.CreativeRepository;
import com.sxzhongf.ad.entity.AdCreative;
import com.sxzhongf.ad.service.ICreativeService;
import com.sxzhongf.ad.vo.CreativeRequestVO;
import com.sxzhongf.ad.vo.CreativeResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CreativeServiceImpl for 创意service
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponseVO createCreative(CreativeRequestVO requestVO) throws AdException {
        //TODO: validate params

        AdCreative creative = creativeRepository.save(
                requestVO.convertToEntity()
        );

        return new CreativeResponseVO(creative.getCreativeId(), creative.getName());
    }
}

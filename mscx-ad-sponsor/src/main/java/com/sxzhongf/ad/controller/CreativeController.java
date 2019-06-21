package com.sxzhongf.ad.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.service.ICreativeService;
import com.sxzhongf.ad.vo.CreativeRequestVO;
import com.sxzhongf.ad.vo.CreativeResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CreativeController for 创意controller
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@Slf4j
@RestController
@RequestMapping("/creative")
public class CreativeController {

    @Autowired
    private ICreativeService creativeService;

    @PostMapping("/create")
    public CreativeResponseVO createCreative(@RequestBody CreativeRequestVO requestVO) throws AdException {
        log.info("Ad-Sponsor::CreativeController -> createCreative {}", JSON.toJSONString(requestVO));
        return creativeService.createCreative(requestVO);
    }

}

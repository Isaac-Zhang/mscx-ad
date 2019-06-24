package com.sxzhongf.ad.client.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.client.ISponsorFeignClient;
import com.sxzhongf.ad.common.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchFeignController for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@RestController
@Slf4j
@RequestMapping(path = "/search-feign")
public class SearchFeignController {

    private final ISponsorFeignClient sponsorFeignClient;

    @Autowired
    public SearchFeignController(ISponsorFeignClient sponsorFeignClient) {
        this.sponsorFeignClient = sponsorFeignClient;
    }


    @GetMapping(path = "/user/get")
    public CommonResponse getUsers(@Param(value = "username") String username) {
        log.info("ad-search::getUsersFeign -> {}", JSON.toJSONString(username));
        CommonResponse commonResponse = sponsorFeignClient.getUsers(username);
        return commonResponse;
    }
}

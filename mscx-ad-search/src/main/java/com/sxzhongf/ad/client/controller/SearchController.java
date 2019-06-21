package com.sxzhongf.ad.client.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.client.vo.AdPlanGetRequestVO;
import com.sxzhongf.ad.client.vo.AdPlanVO;
import com.sxzhongf.ad.common.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * SearchController for search information controller
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@RestController
@Slf4j
@RequestMapping(path = "/search")
public class SearchController {

    private final RestTemplate restTemplate;

    @Autowired
    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/plan/get-ribbon")
    public CommonResponse<List<AdPlanVO>> getAdPlansUseRibbon(@RequestBody AdPlanGetRequestVO requestVO) {
        log.info("ad-search::getAdPlansUseRibbon -> {}", JSON.toJSONString(requestVO));
        return restTemplate.postForEntity(
                "http://mscx-ad-sponsor/ad-sponsor/plan/get", requestVO, CommonResponse.class
        ).getBody();
    }

    @GetMapping(path = "/user/get")
    public CommonResponse getUsers(@Param(value = "username") String username) {
        log.info("ad-search::getUsers -> {}", JSON.toJSONString(username));
        CommonResponse commonResponse = restTemplate.getForObject(
                "http://mscx-ad-sponsor/ad-sponsor/user/get?username={username}", CommonResponse.class, username
        );

        return commonResponse;
    }
}

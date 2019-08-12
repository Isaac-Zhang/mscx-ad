package com.sxzhongf.ad.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.annotation.IgnoreResponseAdvice;
import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.feign.client.vo.AdPlanGetRequestVO;
import com.sxzhongf.ad.feign.client.vo.AdPlanVO;
import com.sxzhongf.ad.search.ISearch;
import com.sxzhongf.ad.search.vo.SearchRequest;
import com.sxzhongf.ad.search.vo.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
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

    private final ISearch search;

    @Autowired
    public SearchController(RestTemplate restTemplate, ISearch search) {
        this.restTemplate = restTemplate;
        this.search = search;
    }

    @PostMapping(path = "/plan/get-ribbon")
    @IgnoreResponseAdvice //如果打上这个标记，我们的统一拦截就会忽略掉它
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

    @PostMapping("/fetchAd")
    public SearchResponse fetchAdCreative(@RequestBody SearchRequest request) {
        log.info("ad-serach: fetchAd ->{}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }
}

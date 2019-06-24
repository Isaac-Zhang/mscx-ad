package com.sxzhongf.ad.client;

import com.sxzhongf.ad.client.vo.AdPlanGetRequestVO;
import com.sxzhongf.ad.client.vo.AdPlanVO;
import com.sxzhongf.ad.common.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * ISponsorFeignClient for service using
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@FeignClient(value = "mscx-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface ISponsorFeignClient {
    @RequestMapping(value = "/ad-sponsor/plan/get", method = RequestMethod.POST)
    CommonResponse<List<AdPlanVO>> getAdPlansUseFeign(@RequestBody AdPlanGetRequestVO requestVO);

    @RequestMapping(value = "/ad-sponsor/user/get", method = RequestMethod.GET)
    CommonResponse getUsers(@Param(value = "username") String username);
}

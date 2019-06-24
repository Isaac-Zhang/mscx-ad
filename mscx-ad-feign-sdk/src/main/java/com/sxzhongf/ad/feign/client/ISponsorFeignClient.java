package com.sxzhongf.ad.feign.client;

import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.feign.client.vo.AdPlanGetRequestVO;
import com.sxzhongf.ad.feign.client.vo.AdPlanVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    /**
     * Feign 埋坑之 如果是Get请求，必须在所有参数前添加{@link RequestParam},不能使用{@link Param}
     * 会被自动转发为POST请求。
     */
    CommonResponse getUsers(@RequestParam(value = "username") String username);
}

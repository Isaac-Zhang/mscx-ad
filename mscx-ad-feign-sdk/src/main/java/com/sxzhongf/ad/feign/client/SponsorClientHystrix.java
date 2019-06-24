package com.sxzhongf.ad.feign.client;

import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.feign.client.vo.AdPlanGetRequestVO;
import com.sxzhongf.ad.feign.client.vo.AdPlanVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SponsorClientHystrix for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Component
public class SponsorClientHystrix implements ISponsorFeignClient {
    @Override
    public CommonResponse<List<AdPlanVO>> getAdPlansUseFeign(AdPlanGetRequestVO requestVO) {

        return new CommonResponse<>(-1, "mscx-ad-sponsor feign & hystrix get plan error.");
    }

    @Override
    public CommonResponse getUsers(String username) {
        return new CommonResponse<>(-1, "mscx-ad-sponsor feign & hystrix get user error.");
    }
}

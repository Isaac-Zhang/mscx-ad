package com.sxzhongf.ad.service;

import com.sxzhongf.ad.SponsorApplication;
import com.sxzhongf.ad.client.vo.PlanGetRequestVO;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.entity.AdPlan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * AdPlanServiceTest for 推广计划单元测试
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {SponsorApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class AdPlanServiceTest {

    @Autowired
    private IPlanService planService;

    @Test
    public void testGetAdPlanByPlanIds() throws AdException {

        List<AdPlan> adPlanByPlanIds = planService.getAdPlanByPlanIds(new PlanGetRequestVO(10L, Arrays.asList(10L)));
        assert adPlanByPlanIds.size() == 1;
        assert adPlanByPlanIds.get(0).getPlanId() == 10L;

        Stream.of(adPlanByPlanIds).forEach(System.out::println);
    }
}

package com.sxzhongf.ad.service;

import com.sxzhongf.ad.SponsorApplication;
import com.sxzhongf.ad.client.vo.UnitRequestVO;
import com.sxzhongf.ad.client.vo.UnitResponseVO;
import com.sxzhongf.ad.common.exception.AdException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * AdUnitServiceTest for 推广单元测试用例
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {SponsorApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdUnitServiceTest {

    @Autowired
    private IUnitService unitService;

    @Test
    @Transactional
    public void testCreateUnit() throws AdException {

        UnitResponseVO unit = unitService.createUnit(
                new UnitRequestVO().builder()
                                   .planId(10L)
                                   .positionType(1)
                                   .budgetFee(100L)
                                   .unitName("test-unit-name")
                                   .build());
        assert unit.getUnitName().equals("test-unit-name");
        System.out.println("测试推广单元：" + unit);

    }
}

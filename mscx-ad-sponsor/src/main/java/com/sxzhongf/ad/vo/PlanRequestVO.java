package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * PlanRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequestVO {

    private Long planId;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(planName)
                && StringUtils.isEmpty(startDate)
                && StringUtils.isEmpty(endDate);
    }

    public boolean updateOrDeleteValidate() {
        return userId != null && planId != null;
    }
}

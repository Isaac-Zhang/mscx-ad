package com.sxzhongf.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * UnitRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequestVO {

    private Long planId;

    private String unitName;

    private Integer positionType;

    private Long budgetFee;

    public boolean creteValidate() {
        return null != planId
                && !StringUtils.isEmpty(unitName)
                && null != positionType
                && null != budgetFee;
    }
}

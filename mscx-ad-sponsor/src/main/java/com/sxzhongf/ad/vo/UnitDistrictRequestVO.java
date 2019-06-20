package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * UnitDistrictRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictRequestVO {

    private List<UnitDistrict> unitDistrictList;

    /**
     * 允许批量操作
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistrict {
        private Long unitId;
        private String province;
        private String city;
    }
}

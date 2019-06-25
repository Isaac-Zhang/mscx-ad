package com.sxzhongf.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnitDistrictTable for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitDistrictTable {
    private Long unitId;
    private String province;
    private String city;
}

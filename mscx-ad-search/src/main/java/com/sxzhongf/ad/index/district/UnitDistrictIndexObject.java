package com.sxzhongf.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UnitDistrictIndexObject for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictIndexObject {
    private Long unitId;
    private String province;
    private String city;

    //Map<String,Set<Long>>
    //Map的key为 province-city
}

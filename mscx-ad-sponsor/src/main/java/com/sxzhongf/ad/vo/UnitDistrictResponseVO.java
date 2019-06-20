package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * UnitDistrictResponseVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictResponseVO {

    private Collection<Long> districtIds;
}

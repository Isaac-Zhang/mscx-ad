package com.sxzhongf.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UnitResponseVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponseVO {
    private Long unitId;
    private String unitName;
}

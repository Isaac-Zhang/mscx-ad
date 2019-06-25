package com.sxzhongf.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnitTable for 需要导出的表字段信息 => 是搜索索引字段一一对应
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitTable {
    private Long unitId;
    private Long planId;
    private Integer unitStatus;
    private Integer positionType;
}

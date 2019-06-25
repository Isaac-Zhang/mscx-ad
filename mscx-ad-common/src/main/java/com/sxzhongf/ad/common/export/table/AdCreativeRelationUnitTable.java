package com.sxzhongf.ad.common.export.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdCreativeRelationUnitTable for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeRelationUnitTable {
    /**
     * 创意/广告ID
     */
    private Long adId;
    /**
     * 推广单元ID
     */
    private Long unitId;
}

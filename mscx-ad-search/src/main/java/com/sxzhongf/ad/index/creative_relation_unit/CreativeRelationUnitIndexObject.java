package com.sxzhongf.ad.index.creative_relation_unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreativeRelationUnitIndexObject for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRelationUnitIndexObject {
    /**
     * 创意/广告ID
     */
    private Long adId;
    /**
     * 推广单元ID
     */
    private Long unitId;
}

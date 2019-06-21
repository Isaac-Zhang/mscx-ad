package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * CreativeUnitRelationshipRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRelationshipRequestVO {

    private Collection<CreativeUnitItemVO> creativeUnitItemVO;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnitItemVO {
        private Long creativeId;
        private Long unitId;
    }
}

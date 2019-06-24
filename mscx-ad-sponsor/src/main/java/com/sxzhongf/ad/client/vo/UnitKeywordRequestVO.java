package com.sxzhongf.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * UnitKeywordRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordRequestVO {

    private List<UnitKeyword> unitKeywordList;
    /**
     * 允许批量操作
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {
        private Long unitId;
        private String keyword;
    }
}

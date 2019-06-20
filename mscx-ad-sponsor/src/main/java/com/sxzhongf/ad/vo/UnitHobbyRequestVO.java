package com.sxzhongf.ad.vo;

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
public class UnitHobbyRequestVO {

    private List<UnitHobby> unitHobbyList;

    /**
     * 允许批量操作
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitHobby {
        private Long unitId;
        private String hobbyTag;
    }
}

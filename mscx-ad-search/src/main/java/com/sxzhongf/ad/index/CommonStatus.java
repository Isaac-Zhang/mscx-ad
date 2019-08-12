package com.sxzhongf.ad.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CommonStatus for 推广单元和推广创意的状态
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/12
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {
    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;
}

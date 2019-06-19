package com.sxzhongf.ad.constant;

import lombok.Getter;

/**
 * CreativeType for 创意类型
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
@Getter
public enum CreativeType {
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TXT(3, "文本");

    private int code;
    private String desc;

    CreativeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

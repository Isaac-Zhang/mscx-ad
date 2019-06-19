package com.sxzhongf.ad.constant;

import lombok.Getter;

/**
 * CreativeMaterielType for 创意物料类型
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
@Getter
public enum CreativeMaterielType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int code;
    private String desc;

    CreativeMaterielType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

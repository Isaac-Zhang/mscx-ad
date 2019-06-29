package com.sxzhongf.ad.index;

import lombok.Getter;

/**
 * AdDataLevel for 广告数据层级
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Getter
public enum AdDataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    AdDataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}

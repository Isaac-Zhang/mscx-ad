package com.sxzhongf.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Device for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private String deviceCode;

    /**
     * 设备device mac地址
     */
    private String deviceMacAddr;

    /**
     * ip
     */
    private String ip;

    /*
     * 机型编码
     */
    private String model;

    /**
     * 分辨率
     */
    private String displaySize;

    /**
     * 屏幕大小
     */
    private String screenSize;

    /**
     * 设备序列号
     */
    private String serialName;
}

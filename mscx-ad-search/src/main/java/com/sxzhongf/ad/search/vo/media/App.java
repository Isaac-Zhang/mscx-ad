package com.sxzhongf.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * App for 广告应用
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用包的名称
     */
    private String packageName;

    /*
     * 对应的请求页面名称
     */
    private String activityName;
}

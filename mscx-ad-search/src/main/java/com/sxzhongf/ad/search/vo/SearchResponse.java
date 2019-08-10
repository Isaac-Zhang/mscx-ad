package com.sxzhongf.ad.search.vo;

import com.sxzhongf.ad.index.creative.CreativeIndexObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SearchResponse for 检索API响应对象
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    //一个广告位，可以展示多个广告
    //Map key为广告位 AdSlot#adSlotCode
    public Map<String, List<Creative>> adSlotRelationAds = new HashMap<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        //展示监控url
        private List<String> showMonitorUrl = Arrays.asList("www.life-runner.com", "www.babydy.cn");
        //点击监控url
        private List<String> clickMonitorUrl = Arrays.asList("www.life-runner.com", "www.babydy.cn");
    }

    /**
     * 我们的检索服务针对的是内存中的索引检索，那么我们就需要一个转换方法
     */
    public static Creative convert(CreativeIndexObject object) {

        return Creative.builder()
                       .adId(object.getAdId())
                       .adUrl(object.getAdUrl())
                       .width(object.getWidth())
                       .height(object.getHeight())
                       .type(object.getType())
                       .materialType(object.getMaterialType())
                       .build();
    }
}

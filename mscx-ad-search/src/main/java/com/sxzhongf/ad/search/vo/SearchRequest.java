package com.sxzhongf.ad.search.vo;

import com.sxzhongf.ad.search.vo.feature.DistrictFeature;
import com.sxzhongf.ad.search.vo.feature.FeatureRelation;
import com.sxzhongf.ad.search.vo.feature.HobbyFeatrue;
import com.sxzhongf.ad.search.vo.feature.KeywordFeature;
import com.sxzhongf.ad.search.vo.media.AdSlot;
import com.sxzhongf.ad.search.vo.media.App;
import com.sxzhongf.ad.search.vo.media.Device;
import com.sxzhongf.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchRequest for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    //媒体方请求标示
    private String mediaId;

    //请求基本信息
    private RequestInfo requestInfo;

    //匹配信息
    private FeatureInfo featureInfo;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {
        private String requestId;

        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private HobbyFeatrue hobbyFeatrue;

        private FeatureRelation relation = FeatureRelation.AND;
    }
}

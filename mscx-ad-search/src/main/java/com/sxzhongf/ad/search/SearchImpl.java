package com.sxzhongf.ad.search;

import com.sxzhongf.ad.index.IndexDataTableUtils;
import com.sxzhongf.ad.index.adunit.AdUnitIndexAwareImpl;
import com.sxzhongf.ad.index.adunit.AdUnitIndexObject;
import com.sxzhongf.ad.search.vo.SearchRequest;
import com.sxzhongf.ad.search.vo.SearchResponse;
import com.sxzhongf.ad.search.vo.feature.DistrictFeature;
import com.sxzhongf.ad.search.vo.feature.FeatureRelation;
import com.sxzhongf.ad.search.vo.feature.HobbyFeatrue;
import com.sxzhongf.ad.search.vo.feature.KeywordFeature;
import com.sxzhongf.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SearchImpl for 实现search 服务
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/10
 */
@Service
@Slf4j
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        //获取请求广告位信息
        List<AdSlot> adSlotList = request.getRequestInfo().getAdSlots();

        //获取三个Feature信息
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        HobbyFeatrue hobbyFeatrue = request.getFeatureInfo().getHobbyFeatrue();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        //Feature关系
        FeatureRelation featureRelation = request.getFeatureInfo().getRelation();


        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlotRelationAds = response.getAdSlotRelationAds();

        for (AdSlot adSlot : adSlotList) {
            Set<Long> targetUnitIdSet;
            //根据流量类型从缓存中获取 初始 广告信息
            Set<Long> adUnitIdSet = IndexDataTableUtils.of(
                    AdUnitIndexAwareImpl.class
            ).match(adSlot.getPositionType());
        }
        return null;
    }
}

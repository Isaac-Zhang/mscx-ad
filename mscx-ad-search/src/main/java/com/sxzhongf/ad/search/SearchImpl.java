package com.sxzhongf.ad.search;

import com.sxzhongf.ad.index.IndexDataTableUtils;
import com.sxzhongf.ad.index.adunit.AdUnitIndexAwareImpl;
import com.sxzhongf.ad.index.adunit.AdUnitIndexObject;
import com.sxzhongf.ad.index.district.UnitDistrictIndexAwareImpl;
import com.sxzhongf.ad.index.hobby.UnitHobbyIndexAwareImpl;
import com.sxzhongf.ad.index.keyword.UnitKeywordIndexAwareImpl;
import com.sxzhongf.ad.search.vo.SearchRequest;
import com.sxzhongf.ad.search.vo.SearchResponse;
import com.sxzhongf.ad.search.vo.feature.DistrictFeature;
import com.sxzhongf.ad.search.vo.feature.FeatureRelation;
import com.sxzhongf.ad.search.vo.feature.HobbyFeatrue;
import com.sxzhongf.ad.search.vo.feature.KeywordFeature;
import com.sxzhongf.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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

            // 根据三个维度过滤
            if (featureRelation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterHobbyFeature(adUnitIdSet, hobbyFeatrue);
                filterDistrictFeature(adUnitIdSet, districtFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                getOrRelationUnitIds(adUnitIdSet, keywordFeature, hobbyFeatrue, districtFeature);
            }
        }


        return null;
    }

    /**
     * 获取三个维度各自满足时的广告id
     */
    private Set<Long> getOrRelationUnitIds(Set<Long> adUnitIdsSet,
                                           KeywordFeature keywordFeature,
                                           HobbyFeatrue hobbyFeatrue,
                                           DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIdsSet)) return Collections.EMPTY_SET;

        // 我们在处理的时候，需要对副本进行处理，大家可以考虑一下为什么需要这么做？
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdsSet);
        Set<Long> hobbyUnitIdSet = new HashSet<>(adUnitIdsSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdsSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterHobbyFeature(hobbyUnitIdSet, hobbyFeatrue);
        filterDistrictFeature(districtUnitIdSet, districtFeature);

        // 返回它们的并集
        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, hobbyUnitIdSet),
                        districtUnitIdSet
                )
        );
    }

    /**
     * 根据传递的关键词过滤
     */
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) return;
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            // 如果存在需要过滤的关键词，查找索引实例对象进行过滤处理
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> IndexDataTableUtils.of(UnitKeywordIndexAwareImpl.class)
                                                   .match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 根据传递的兴趣信息过滤
     */
    private void filterHobbyFeature(Collection<Long> adUnitIds, HobbyFeatrue hobbyFeatrue) {
        if (CollectionUtils.isEmpty(adUnitIds)) return;
        // 如果存在需要过滤的兴趣，查找索引实例对象进行过滤处理
        if (CollectionUtils.isNotEmpty(hobbyFeatrue.getHobbys())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> IndexDataTableUtils.of(UnitHobbyIndexAwareImpl.class)
                                                   .match(adUnitId, hobbyFeatrue.getHobbys())
            );
        }
    }

    /**
     * 根据传递的地域信息过滤
     */
    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) return;
        // 如果存在需要过滤的地域信息，查找索引实例对象进行过滤处理
        if (CollectionUtils.isNotEmpty(districtFeature.getProvinceAndCities())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> {
                        return IndexDataTableUtils.of(UnitDistrictIndexAwareImpl.class)
                                                  .match(adUnitId, districtFeature.getProvinceAndCities());
                    }
            );
        }
    }
}

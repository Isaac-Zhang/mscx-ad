package com.sxzhongf.ad.search;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sxzhongf.ad.index.CommonStatus;
import com.sxzhongf.ad.index.IndexDataTableUtils;
import com.sxzhongf.ad.index.adunit.AdUnitIndexAwareImpl;
import com.sxzhongf.ad.index.adunit.AdUnitIndexObject;
import com.sxzhongf.ad.index.creative.CreativeIndexAwareImpl;
import com.sxzhongf.ad.index.creative.CreativeIndexObject;
import com.sxzhongf.ad.index.creative_relation_unit.CreativeRelationUnitIndexAwareImpl;
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

    /**
     * 查询广告容错方法
     *
     * @param e 第二个参数可以不指定，如果需要跟踪错误，就指定上
     * @return 返回一个空map 对象
     */
    public SearchResponse fetchAdsFallback(SearchRequest request, Throwable e) {

        System.out.println("查询广告失败，进入容错降级 : %s" + e.getMessage());
        return new SearchResponse().builder().adSlotRelationAds(Collections.emptyMap()).build();
    }

//    @HystrixCommand(fallbackMethod = "fetchAdsFallback")
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
                targetUnitIdSet = getOrRelationUnitIds(adUnitIdSet, keywordFeature, hobbyFeatrue, districtFeature);
            }
            //获取 推广计划 对象list
            List<AdUnitIndexObject> unitIndexObjects = IndexDataTableUtils.of(AdUnitIndexAwareImpl.class)
                                                                          .fetch(targetUnitIdSet);
            //根据状态过滤数据
            filterAdUnitAndPlanStatus(unitIndexObjects, CommonStatus.VALID);

            //获取 推广创意 id list
            List<Long> creativeIds = IndexDataTableUtils.of(CreativeRelationUnitIndexAwareImpl.class)
                                                        .selectAdCreativeIds(unitIndexObjects);
            //根据 推广创意ids获取推广创意
            List<CreativeIndexObject> creativeIndexObjects = IndexDataTableUtils.of(CreativeIndexAwareImpl.class)
                                                                                .fetch(creativeIds);

            //根据 广告位adslot 实现对创意数据的过滤
            filterCreativeByAdSlot(creativeIndexObjects, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());

            //一个广告位可以展示多个广告，也可以仅展示一个广告，具体根据业务来定
            adSlotRelationAds.put(
                    adSlot.getAdSlotCode(),
                    buildCreativeResponse(creativeIndexObjects)
            );
        }

        log.info("请求返回结果:{} - {} \n", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
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

    /**
     * 根据状态信息过滤数据
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitIndexObject> unitIndexObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitIndexObjects)) return;

        //同时判断推广单元和推广计划的状态
        CollectionUtils.filter(
                unitIndexObjects,
                unitIndexObject -> unitIndexObject.getUnitStatus().equals(status.getStatus()) &&
                        unitIndexObject.getAdPlanIndexObject().getPlanStatus().equals(status.getStatus())
        );
    }

    /**
     * 根据广告位类型以及参数获取展示的合适广告信息
     *
     * @param creativeIndexObjects 所有广告创意
     * @param width                广告位width
     * @param height               广告位height
     */
    private void filterCreativeByAdSlot(List<CreativeIndexObject> creativeIndexObjects,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type) {
        if (CollectionUtils.isEmpty(creativeIndexObjects)) return;

        CollectionUtils.filter(
                creativeIndexObjects,
                creative -> {
                    //审核状态必须是通过
                    return creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                            && creative.getWidth().equals(width)
                            && creative.getHeight().equals(height)
                            && type.contains(creative.getType());
                }
        );
    }

    /**
     * 从创意列表中随机获取一条创意广告返回出去
     *
     * @param creativeIndexObjects 创意广告list
     */
    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeIndexObject> creativeIndexObjects) {
        if (CollectionUtils.isEmpty(creativeIndexObjects)) return Collections.EMPTY_LIST;

        //随机获取一个广告创意，也可以实现优先级排序，也可以根据权重值等等，具体根据业务
        CreativeIndexObject randomObject = creativeIndexObjects.get(
                Math.abs(new Random().nextInt()) % creativeIndexObjects.size()
        );
        //List<SearchResponse.Creative> result = new ArrayList<>();
        //result.add(SearchResponse.convert(randomObject));

        return Collections.singletonList(
                SearchResponse.convert(randomObject)
        );
    }
}

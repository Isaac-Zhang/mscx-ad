package com.sxzhongf.ad.search;

import com.sxzhongf.ad.AdSearchApplication;
import com.sxzhongf.ad.search.vo.SearchRequest;
import com.sxzhongf.ad.search.vo.SearchResponse;
import com.sxzhongf.ad.search.vo.feature.DistrictFeature;
import com.sxzhongf.ad.search.vo.feature.FeatureRelation;
import com.sxzhongf.ad.search.vo.feature.HobbyFeatrue;
import com.sxzhongf.ad.search.vo.feature.KeywordFeature;
import com.sxzhongf.ad.search.vo.media.AdSlot;
import com.sxzhongf.ad.search.vo.media.App;
import com.sxzhongf.ad.search.vo.media.Device;
import com.sxzhongf.ad.search.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SearchTest for 搜索服务测试用例
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdSearchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest().builder()
                                                   .mediaId("isaac-search-mediaId")
                                                   .requestInfo(new SearchRequest.RequestInfo(
                                                           "request id",
                                                           Arrays.asList(
                                                                   new AdSlot().builder()
                                                                               .adSlotCode("slot code")
                                                                               .height(800)
                                                                               .minCpm(1024)
                                                                               .positionType(1)
                                                                               .type(Arrays.asList(1))
                                                                               .build()
                                                           ),
                                                           buildSimpleApp(),
                                                           buildSimpleGeo(),
                                                           buildSimpleDevice()
                                                   ))
                                                   .featureInfo(
                                                           buildSimpleFeatureInfo(
                                                                   Arrays.asList("汽车", "火车", "飞机"),
                                                                   Collections.singletonList(
                                                                           new DistrictFeature.ProvinceAndCity(
                                                                                   "陕西省", "西安市"
                                                                           )
                                                                   ),
                                                                   Arrays.asList("爬山", "写代码", "飞机"),
                                                                   FeatureRelation.OR
                                                           )
                                                   )
                                                   .build();
        SearchResponse response = search.fetchAds(request);
//        assert response.adSlotRelationAds.get(0).contains("key");

        System.out.println("开始查询广告拉：" + response);
    }

    /**
     * 创建demo {@link App}
     */
    private App buildSimpleApp() {
        return new App().builder()
                        .activityName("simple App activityName")
                        .appCode("simple App appCode")
                        .appName("simple app name")
                        .packageName("simple app package name")
                        .build();
    }

    /**
     * 创建demo {@link Geo}
     */
    private Geo buildSimpleGeo() {
        return new Geo().builder()
                        .longitude(Float.valueOf("100.2222222"))
                        .latitude(Float.valueOf("38.8888888"))
                        .city("xiaan")
                        .province("shaanxi")
                        .build();
    }

    /**
     * 创建demo {@link Device}
     */
    private Device buildSimpleDevice() {
        return new Device().builder()
                           .deviceCode("simple device code")
                           .deviceMacAddr("simple mac addr")
                           .displaySize("simple display size")
                           .ip("127.0.0.1")
                           .model("simple model")
                           .screenSize("simple screen size")
                           .serialName("simple serial name")
                           .build();
    }

    private SearchRequest.FeatureInfo buildSimpleFeatureInfo(
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> hobbys,
            FeatureRelation featureRelation
    ) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new HobbyFeatrue(hobbys),
                featureRelation
        );
    }
}

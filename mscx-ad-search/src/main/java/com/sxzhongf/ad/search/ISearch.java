package com.sxzhongf.ad.search;

import com.sxzhongf.ad.search.vo.SearchRequest;
import com.sxzhongf.ad.search.vo.SearchResponse;

/**
 * ISearch for 请求接口,
 * 根据广告请求对象，获取广告响应信息
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/1
 */
@FunctionalInterface
public interface ISearch {

    /**
     * 根据请求返回广告结果
     */
    SearchResponse fetchAds(SearchRequest request);
}

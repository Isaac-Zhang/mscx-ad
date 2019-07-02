package com.sxzhongf.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AdSlot for 广告位
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    //广告位编码
    private String adSlotCode;

    //流量类型
    private Integer positionType;

    //广告位宽和高
    private Integer height;
    private Integer width;

    //广告位物料类型：图片/视频等
    private List<Integer> type;

    //最低出价
    private Integer minCpm;
}

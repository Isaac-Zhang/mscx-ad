package com.sxzhongf.ad.index.adunit;

/**
 * AdUnitConstants for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/10
 */

public class AdUnitConstants {

    public static class PositionType {
        //App启动时展示的、展示时间短暂的全屏化广告形式。
        public static final int KAIPING = 1;
        //电影开始之前的广告
        public static final int TIEPIAN = 2;
        //电影播放中途广告
        public static final int TIEPIAN_MIDDLE = 4;
        //暂停视频时候播放的广告
        public static final int TIEPIAN_PAUSE = 8;
        //视频播放完
        public static final int TIEPIAN_POST = 16;
    }
}

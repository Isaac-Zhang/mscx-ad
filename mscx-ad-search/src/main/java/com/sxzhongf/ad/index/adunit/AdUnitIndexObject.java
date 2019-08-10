package com.sxzhongf.ad.index.adunit;

import com.sxzhongf.ad.index.adplan.AdPlanIndexObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnitIndexObject for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitIndexObject {

    private Long unitId;
    private Long planId;
    private Integer unitStatus;
    private Integer positionType;

    private AdPlanIndexObject adPlanIndexObject;

    void update(AdUnitIndexObject newObject) {
        if (null != newObject.getUnitId()) {
            this.unitId = newObject.getUnitId();
        }
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }
        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (null != newObject.getPositionType()) {
            this.positionType = newObject.getPositionType();
        }
        if (null != newObject.getAdPlanIndexObject()) {
            this.adPlanIndexObject = newObject.getAdPlanIndexObject();
        }
    }

    public static boolean isAdSlotType(int adSlotType, int positionType) {
        switch (adSlotType) {
            case AdUnitConstants.PositionType.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.PositionType.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstants.PositionType.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstants.PositionType.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstants.PositionType.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }

    /**
     * 与运算，低位取等，高位补零。
     * 如果 > 0，则为开屏
     */
    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstants.PositionType.KAIPING) > 0;
    }

    /**
     * ? & 0000 0100
     * 如果为 positionType 1
     * 0000 0001
     * 0000 0100
     * 0000 0000 < 0
     * 如果为 positionType 2
     * 0000 0010
     * 0000 0100
     * 0000 0000 < 0
     * 如果为 positionType 8
     * 0000 1000
     * 0000 0100
     * 0000 0000 < 0
     * 如果为 positionType 16
     * 0001 0000
     * 0000 0100
     * 0000 0000 < 0
     */
    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstants.PositionType.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstants.PositionType.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstants.PositionType.TIEPIAN_POST) > 0;
    }

    private static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstants.PositionType.TIEPIAN) > 0;
    }
}

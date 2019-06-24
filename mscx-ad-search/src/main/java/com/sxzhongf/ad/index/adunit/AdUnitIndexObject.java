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
}

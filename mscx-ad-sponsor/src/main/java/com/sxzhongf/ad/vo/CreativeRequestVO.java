package com.sxzhongf.ad.vo;

import com.sxzhongf.ad.constant.CommonStatus;
import com.sxzhongf.ad.entity.AdCreative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CreativeRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequestVO {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Long duration;
    private Long userId;
    private String url;

    public AdCreative convertToEntity() {
        AdCreative model = new AdCreative();
        model.setName(name);
        model.setType(type);
        model.setMaterialType(materialType);
        model.setHeight(height);
        model.setWidth(width);
        model.setDuration(duration);
        model.setAuditStatus(CommonStatus.VALID.getStatus());
        model.setUserId(userId);
        model.setUrl(url);
        model.setCreateTime(new Date());
        model.setUpdateTime(model.getCreateTime());

        return model;
    }
}

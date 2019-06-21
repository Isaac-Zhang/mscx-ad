package com.sxzhongf.ad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * AdCreative for 广告创意表
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_creative")
public class AdCreative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creative_id")
    private Long creativeId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 创意类型：标示当前创意是视频/图片等等
     */
    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 物料的类型，比如图片可以是 bmp,jpge等等
     */
    @Basic
    @Column(name = "material_type", nullable = false)
    private Integer materialType;

    /**
     * 物料的高度
     */
    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;

    /**
     * 物料的宽度
     */
    @Basic
    @Column(name = "width", nullable = false)
    private Integer width;

    /**
     * 物料的大小，单位可以是kb,byte
     */
    @Basic
    @Column(name = "size", nullable = false)
    private Integer size;

    /**
     * 持续时长，只有视频不为0
     */
    @Basic
    @Column(name = "duration", nullable = false)
    private Long duration;

    /**
     * 审核状态
     */
    @Basic
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 物料存储地址
     */
    @Basic
    @Column(name = "url", nullable = false)
    private String url;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdCreative(String name, Integer type, Integer materielType,
                      Integer height, Integer width, Integer size, Long duration,
                      Integer auditStatus, Long userId, String url) {
        this.name = name;
        this.type = type;
        this.materialType = materielType;
        this.height = height;
        this.width = width;
        this.size = size;
        this.duration = duration;
        this.auditStatus = auditStatus;
        this.userId = userId;
        this.url = url;
    }
}

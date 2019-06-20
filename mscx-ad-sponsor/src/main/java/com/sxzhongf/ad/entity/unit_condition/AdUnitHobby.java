package com.sxzhongf.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * AdUnitHobby for 推广单元对应的维度之兴趣维度
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_hobby")
public class AdUnitHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id", nullable = false)
    private Long hobbyId;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "hobby_tag", nullable = false)
    private String hobbyTag;


    public AdUnitHobby(Long unitId, String hobbyTag) {
        this.unitId = unitId;
        this.hobbyTag = hobbyTag;
    }
}

package com.sxzhongf.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * RelationshipCreativeUnit for 创意 & 推广单元 关联关系表
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relationship_creative_unit")
public class RelationshipCreativeUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "creative_id", nullable = false)
    private Long creativeId;

    public RelationshipCreativeUnit(Long unitId, Long creativeId) {
        this.unitId = unitId;
        this.creativeId = creativeId;
    }
}

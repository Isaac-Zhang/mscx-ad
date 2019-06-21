package com.sxzhongf.ad.dao;

import com.sxzhongf.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdPlanRepository for 广告计划数据库操作类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    AdPlan findByPlanIdAndUserId(Long planId, Long userId);

    List<AdPlan> findAllByPlanIdAndUserId(List<Long> planIds, Long userId);

    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);
}

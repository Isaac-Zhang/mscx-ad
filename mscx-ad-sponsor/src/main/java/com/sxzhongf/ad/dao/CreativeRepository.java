package com.sxzhongf.ad.dao;

import com.sxzhongf.ad.entity.AdCreative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CreativeRepository for 创意表数据库操作类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
public interface CreativeRepository extends JpaRepository<AdCreative, Long> {

}

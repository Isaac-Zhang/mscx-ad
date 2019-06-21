package com.sxzhongf.ad.dao;

import com.sxzhongf.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdUserRepository for 用户数据库操作接口
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/19
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名称获取用户
     *
     * @param username 名称
     * @return 用户对象
     */
    AdUser findByUserName(String username);

    List<AdUser> findAllByUserName(String userName);
}

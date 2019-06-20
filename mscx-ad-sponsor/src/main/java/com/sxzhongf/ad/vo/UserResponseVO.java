package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * UserResponseVO for 用户响应VO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseVO {
    private Long userId;
    private String userName;
    private String token;
    private Date createTime;
    private Date updateTime;
}

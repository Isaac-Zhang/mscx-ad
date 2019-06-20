package com.sxzhongf.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * UserRequestVO for 创建用户请求对象VO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestVO {

    private String userName;

    public boolean validate() {
        return !StringUtils.isEmpty(userName);
    }
}

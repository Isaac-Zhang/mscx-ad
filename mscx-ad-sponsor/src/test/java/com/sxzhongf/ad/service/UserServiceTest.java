package com.sxzhongf.ad.service;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.client.vo.UserRequestVO;
import com.sxzhongf.ad.client.vo.UserResponseVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceTest for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
//    @Transactional
    public void testCreateUser() throws AdException {
        UserRequestVO userRequestVO = new UserRequestVO("Isaac Zhang");

        UserResponseVO responseVO = userService.createUser(userRequestVO);

        assert responseVO.getUserName() == "Isaac Zhang";

        System.out.printf("创建用户: %s", JSON.toJSONString(responseVO));

    }
}

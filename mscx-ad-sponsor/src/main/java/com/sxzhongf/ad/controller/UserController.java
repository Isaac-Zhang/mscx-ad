package com.sxzhongf.ad.controller;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.vo.CommonResponse;
import com.sxzhongf.ad.service.IUserService;
import com.sxzhongf.ad.client.vo.UserRequestVO;
import com.sxzhongf.ad.client.vo.UserResponseVO;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * UserController for 用户controller
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(path = "/create")
    public UserResponseVO createUser(@RequestBody UserRequestVO requestVO) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(requestVO));
        return userService.createUser(requestVO);
    }

    @GetMapping(path = "/get")
    public CommonResponse getUserList(@Param(value = "username") String username) throws AdException {
        log.info("ad-sponsor: getUserList -> {}", JSON.toJSONString(username));
        return new CommonResponse(userService.findAllByUserName(username));
    }
}

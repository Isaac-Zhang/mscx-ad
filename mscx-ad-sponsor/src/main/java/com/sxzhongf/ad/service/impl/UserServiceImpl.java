package com.sxzhongf.ad.service.impl;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.utils.CommonUtils;
import com.sxzhongf.ad.constant.Constants;
import com.sxzhongf.ad.dao.AdUserRepository;
import com.sxzhongf.ad.entity.AdUser;
import com.sxzhongf.ad.service.IUserService;
import com.sxzhongf.ad.vo.UserRequestVO;
import com.sxzhongf.ad.vo.UserResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * UserServiceImpl for 用户service
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 创建用户
     *
     * @param userRequestVO {@link UserRequestVO}
     * @return result {@link UserResponseVO}
     * @since 2019/6/19 9:08 PM
     */
    @Override
    @Transactional
    public UserResponseVO createUser(UserRequestVO userRequestVO) throws AdException {
        if (!userRequestVO.validate()) {
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        //查重
        AdUser existUser = userRepository.findByUserName(userRequestVO.getUserName());
        if (existUser != null) {
            throw new AdException(Constants.ErrorMessage.USER_EXIST);
        }
        AdUser user = userRepository.save(new AdUser(userRequestVO.getUserName(), CommonUtils.md5(userRequestVO.getUserName())));
        return new UserResponseVO(user.getUserId(), user.getUserName(), user.getToken(),
                user.getCreateTime(), user.getUpdateTime());
    }
}

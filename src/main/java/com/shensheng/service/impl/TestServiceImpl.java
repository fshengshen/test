package com.shensheng.service.impl;

import com.shensheng.persistence.beans.User;
import com.shensheng.persistence.mapper.UserInfoMapper;
import com.shensheng.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shensheng on 2017/3/19.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    private Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public User login() {
        User user = new User();
        user.setUserName("1");
        user.setPassword("1");
        user = userInfoMapper.check(user);
        logger.debug("ttt");
        return user;
    }
}

package com.shensheng.service.impl;

import com.shensheng.persistence.beans.User;
import com.shensheng.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by shensheng on 2017/3/19.
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void login() {
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
    }
}

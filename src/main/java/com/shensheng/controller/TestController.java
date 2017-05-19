package com.shensheng.controller;

import com.shensheng.persistence.beans.User;
import com.shensheng.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shensheng on 2017/3/19.
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    /**
     * git test
     * @return String
     */
    @GetMapping(value = "test")
    public User test(){
        return testService.login();
    }

    @GetMapping(value = "1")
    public String hello() {
        return "1";
    }
}

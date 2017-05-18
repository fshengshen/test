package com.shensheng.controller;

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
    //test1
    @GetMapping(value = "test")
    public String test(){
        System.out.print(1324165);
        testService.login();
        return "1";
    }
}

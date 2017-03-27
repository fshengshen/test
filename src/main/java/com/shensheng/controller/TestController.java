package com.shensheng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shensheng on 2017/3/19.
 */
@RestController
public class TestController {

    /**
     * git test
     * @return
     */
    //test1
    @RequestMapping(value = "test")
    public String test(){
        System.out.print(1);
        return "1";
    }
}

package com.shensheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shensheng on 2017/3/19.
 */
@Controller
public class TestController {

    @RequestMapping(value = "test")
    public String test(){
        System.out.print(1);
        return "1";
    }
}

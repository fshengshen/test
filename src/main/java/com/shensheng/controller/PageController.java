package com.shensheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 2017/5/23.
 */
@Controller
public class PageController {
    @GetMapping(value = "1")
    public String hello() {
        return "1";
    }
}

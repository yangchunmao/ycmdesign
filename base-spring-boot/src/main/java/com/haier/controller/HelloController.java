package com.haier.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2017/12/14.
 * @author  ycm
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(){
        return "hello base spring boot!";
    }
}

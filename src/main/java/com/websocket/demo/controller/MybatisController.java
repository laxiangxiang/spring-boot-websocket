package com.websocket.demo.controller;

import com.websocket.demo.service.MybatisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MybatisController {

    @Resource
    private MybatisService mybatisTest;

    @RequestMapping(value = "/mybatisTest/register",method = RequestMethod.GET)
    @ResponseBody
    public boolean mybatisTestRegister(){
        return mybatisTest.test();
    }
}

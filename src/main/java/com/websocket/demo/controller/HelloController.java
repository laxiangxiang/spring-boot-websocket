package com.websocket.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * thymeleaf 和jsp并不兼容，访问会报错。在springboot中不推荐使用jsp
 */
@Controller
public class HelloController {

    @Value("${application.hello:Hello}")
    private String hello;

    @RequestMapping("/helloJsp")
    public String helloJsp(Map<String,Object> map){
        System.out.println("HelloController.helloJsp().hello="+hello);
        map.put("hello",hello);
        return "helloJsp";
    }
}

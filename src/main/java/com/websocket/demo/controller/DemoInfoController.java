package com.websocket.demo.controller;

import com.websocket.demo.dto.DemoInfo;
import com.websocket.demo.service.DemoInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class DemoInfoController {

    @Resource
    private DemoInfoService demoInfoService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        DemoInfo loaded = demoInfoService.findById(1L);
        System.out.println("loaded="+loaded);
        DemoInfo cached = demoInfoService.findById(1L);
        System.out.println("cached="+cached);
        loaded = demoInfoService.findById(1L);
        System.out.println("loaded="+loaded);
        return "ok";
    }

    @RequestMapping(value = "/deleted",method = RequestMethod.DELETE)
    public String deleted(@RequestParam Long id){
        demoInfoService.deletedFromCache(id);
        return "ok";
    }


}

package com.websocket.demo.controller;

import com.websocket.demo.domain.DemoInfo;
import com.websocket.demo.service.DemoInfoService;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class DemoInfoController {

    @Resource
    private DemoInfoService demoInfoService;
//=========================redis缓存测试controller=========================
    @RequestMapping(value = "/test/redisCache",method = RequestMethod.GET)
    @ResponseBody
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
    @ResponseBody
    public String deleted(@RequestParam Long id){
        demoInfoService.deletedFromCache(id);
        return "ok";
    }

//=========================EhCache缓存测试controller=========================


    @RequestMapping(value = "/test/testEhCache",method = RequestMethod.GET)
    @ResponseBody
    public String testEhCache(){

        //存入两条数据.
        DemoInfo demoInfo = new DemoInfo();
        demoInfo.setName("张三");
        demoInfo.setPassword("123456");
        DemoInfo demoInfo2 = demoInfoService.save(demoInfo);

        //不走缓存.
        System.out.println(demoInfoService.findById2(demoInfo2.getId()));
        //走缓存.
        System.out.println(demoInfoService.findById2(demoInfo2.getId()));


        demoInfo = new DemoInfo();
        demoInfo.setName("李四");
        demoInfo.setPassword("123456");
        DemoInfo demoInfo3 = demoInfoService.save(demoInfo);

        //不走缓存.
        System.out.println(demoInfoService.findById2(demoInfo3.getId()));
        //走缓存.
        System.out.println(demoInfoService.findById2(demoInfo3.getId()));

        System.out.println("============修改数据=====================");
        //修改数据.
        DemoInfo updated = new DemoInfo();
        updated.setName("李四-updated");
        updated.setPassword("123456");
        updated.setId(demoInfo3.getId());
        try {
            System.out.println(demoInfoService.update(updated));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //不走缓存.
        System.out.println(demoInfoService.findById2(updated.getId()));

        return "ok";
    }


}

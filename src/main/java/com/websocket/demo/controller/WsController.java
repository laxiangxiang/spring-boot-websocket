package com.websocket.demo.controller;

import com.websocket.demo.dto.People;
import com.websocket.demo.dto.RequestMessage;
import com.websocket.demo.dto.ResponseMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/send")
public class WsController {

    @Resource
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * @MessageMapping注解和我们之前使用的@RequestMapping类似,接收客户端发送消息的路径
     * @SendTo注解表示当服务器有消息需要推送的时候，会对订阅了@SendTo中路径的浏览器发送消息
     * @param message
     * @return
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message){
        System.out.println(message.getName());
        return new ResponseMessage("welcome,"+message.getName()+"!");
    }


    @MessageMapping("/test")
    @SendTo("/topic/getMessage")
    public People sayTest(People people){
        People people1 = new People(people.getName(),people.getAge());
        System.out.println("people:"+people);
        return people1;
    }

    /**
     * 主动发送消息
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value = "通过接口利用websocket主动向前端页面发送消息",notes = "向前端发送people对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "peopleName",value = "姓名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "peopleAge",value = "年龄",required = true,dataType = "String")
    })
    @RequestMapping(value = "/send/withSocket",method = RequestMethod.GET)
    public People sendPeople(@RequestParam(name = "peopleName") String name,@RequestParam("peopleAge") String age){
        People people = new People(name,age);
        //发送给指定用户
        //simpMessageSendingOperations.convertAndSendToUser("1","/topic/getMessage",people);
        //广播
        simpMessageSendingOperations.convertAndSend("/topic/getMessage",people);
        return people;
    }

    @ApiOperation(value = "接口通讯测试",notes = "前端请求，访问测试")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Boolean test(){
        return true;
    }

}

package com.websocket.demo.controller;

import com.websocket.demo.dto.People;
import com.websocket.demo.dto.RequestMessage;
import com.websocket.demo.dto.ResponseMessage;
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
    @RequestMapping(value = "/send/withSocket",method = RequestMethod.GET)
    public People sendPeople(@RequestParam String name,@RequestParam String age){
        People people = new People(name,age);
        //发送给指定用户
        //simpMessageSendingOperations.convertAndSendToUser("1","/topic/getMessage",people);
        //广播
        simpMessageSendingOperations.convertAndSend("/topic/getMessage",people);
        return people;
    }

}

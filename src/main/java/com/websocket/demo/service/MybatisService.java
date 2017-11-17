package com.websocket.demo.service;

import com.websocket.demo.mybatisMapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class MybatisService {

    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 36000,rollbackFor = Exception.class)
    public boolean test(){
        return userMapper.insertUsers("样",24);
    }
}

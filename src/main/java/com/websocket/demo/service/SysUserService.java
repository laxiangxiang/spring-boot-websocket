package com.websocket.demo.service;

import com.websocket.demo.domain.SysUser;
import com.websocket.demo.mybatisMapper.SysUserMapper;
import com.websocket.demo.service.exception.CustomException;
import com.websocket.demo.service.util.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     认证过程：
     根据用户身份（账号）查询数据库，如果查询不到用户不存在
     对输入的密码 和数据库密码 进行比对，如果一致，认证通过
     */
    public SysUser authenticat(String userCode,String password)throws Exception{
        //根据用户账号查询数据库
        SysUser sysUser = sysUserMapper.getSysUserByUserCode(userCode);
        if (sysUser == null){
            throw new CustomException("用户账号不存在",-1);
        }
        //数据库密码 (md5密码 )
        String password_db = sysUser.getPassword();
        //对输入的密码 和数据库密码 进行比对，如果一致，认证通过
        //对页面输入的密码 进行md5加密
        String password_input_md5 = MD5.getMD5ofStr(password);
        if (!password_input_md5.equals(password_db)){
            System.err.println("用户名或密码错误");
            throw new CustomException("用户名或密码错误",-2);
        }

        String userId = sysUser.getId();

        return sysUser;
    }


}

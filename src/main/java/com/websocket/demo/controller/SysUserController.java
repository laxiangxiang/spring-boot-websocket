package com.websocket.demo.controller;

import com.websocket.demo.domain.SysUser;
import com.websocket.demo.service.SysUserService;
import com.websocket.demo.service.exception.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpSession session,String userCode,String password,String randomCode) throws Exception{

        //校验验证码
        //从session获取正确的验证码
        String validateCode = (String) session.getAttribute("validateCode");
        if (!randomCode.equals(validateCode)){
            throw new CustomException("验证码错误！",-3);
        }
        //用户身份认证
        SysUser sysUser = sysUserService.authenticat(userCode,password);
        //记录session
        session.setAttribute("activeUser",sysUser);
        //重定向到商品查询页面
        return "redirect:/first";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session) throws Exception{
        //session失效
        session.invalidate();
        //重定向到商品查询页面
        return "redirect:/items/queryItems";
    }
}

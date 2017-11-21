package com.websocket.demo.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;

@Component
@Aspect
public class MailSenderAOP {

    private static Logger logger = LoggerFactory.getLogger(MailSenderAOP.class);
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.websocket.demo.controller.MailSenderController.*(..))")
    public void send(){};

    @Before("send()")
    public void beforeSend(JoinPoint joinPoint){
        logger.info("开始发送邮件。。。");
        startTime.set(System.currentTimeMillis());
        //记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning("send()")
    public void afterSend(JoinPoint joinPoint){
        logger.info("发送耗时（毫秒）："+(System.currentTimeMillis()-startTime.get()));
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String path = request.getSession().getServletContext().getRealPath("/");
        System.out.println("转存文件路径："+path);
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files){
            if (!file1.isDirectory()){//如果不是目录
                //删除文件
                System.out.println("正在删除转存文件："+file1.getName());
                file1.delete();
                System.out.println("删除成功");
            }
        }
    }
}

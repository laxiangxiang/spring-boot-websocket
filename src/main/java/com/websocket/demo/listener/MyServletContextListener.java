package com.websocket.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContext监听器
 * 使用@WebListener注解，实现ServletContextListener接口
 */
@WebListener
public class MyServletContextListener implements ServletContextListener{
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext销毁");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext初始化");
    }
}

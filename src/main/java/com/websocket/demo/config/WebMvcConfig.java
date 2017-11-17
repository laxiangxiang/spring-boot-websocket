package com.websocket.demo.config;

import com.websocket.demo.intercepteor.MyInterceptor1;
import com.websocket.demo.intercepteor.MyInterceptor2;
import com.websocket.demo.intercepteor.SysUserLoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 以前写Spring MVC的时候，要添加一个新页面访问总是要新增一个Controller或者在已有的一个Controller中新增一个方法，
 * 然后再跳转到设置的页面上去。考虑到大部分应用场景中View和后台都会有数据交互，这样的处理也无可厚非，
 * 不过我们肯定也有只是想通过一个URL Mapping然后不经过Controller处理直接跳转到页面上的需求！
 * 今天在做Spring Security相关配置的时候偶然发现了Spring也为我们提供了一个办法！那就是 WebMvcConfigurerAdapter ！
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        super.addViewControllers(registry);
        registry.addViewController("/ws").setViewName("/ws");
        registry.addViewController("/ws2").setViewName("/ws2");
        registry.addViewController("/websocket/index.html").setViewName("/websocket/index.html");
    }

    /**
     * 添加自定义拦截器
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
        registry.addInterceptor(new SysUserLoginIntercepter()).addPathPatterns("/login");
    }
}

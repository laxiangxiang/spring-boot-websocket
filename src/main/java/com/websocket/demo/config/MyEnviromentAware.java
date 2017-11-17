package com.websocket.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 主要是@Configuration，实现接口：EnvironmentAware就能获取到系统环境信息;
 * 咱们这里介绍凡是被spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment
 * 可以在工程启动时，获取到系统环境变量和application配置文件中的变量
 *
 * 我们还可以通过@ConfigurationProperties 读取application属性配置文件中的属性。
 *
 */
@Configuration
public class MyEnviromentAware implements EnvironmentAware{

    @Value("${spring.mvc.view.prefix}")
    private String mvcPrefix;

    /**
     *注意重写的方法 setEnvironment 是在系统启动的时候被执行。
     */
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("mvcPrefix:"+mvcPrefix);
        //通过enviroment获取系统属性
        System.out.println(environment.getProperty("java_home"));
        //通过enviroment同样能获取到application.yml配置的属性
        System.out.println(environment.getProperty("spring.mvc.view.prefix"));
        //获取到前缀是”spring.mvc.view.“的属性列表值
        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment,"spring.mvc.view.");
        System.out.println("spring.mvc.view.prefix="+relaxedPropertyResolver.getProperty("prefix"));
        System.out.println("spring.mvc.view.suffix="+relaxedPropertyResolver.getProperty("suffix"));

    }
}

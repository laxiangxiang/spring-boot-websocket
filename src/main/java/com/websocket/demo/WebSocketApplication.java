package com.websocket.demo;

import com.websocket.demo.servlet.Myservlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * @author LXX
 */
@SpringBootApplication
@ServletComponentScan
/**
 * 在 SpringBootApplication上使用@ServletComponentScan 注解后，
 * Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
 */
//如果不加MapperScan注解需要在Mapper接口上添加@Mapper注解
//@MapperScan(value = "com.websocket.demo.mybatisMapper")
@EnableTransactionManagement//事务管理注解
public class WebSocketApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebSocketApplication.class, args);
		SpringApplication application = new SpringApplication(WebSocketApplication.class);
		/*
         * Banner.Mode.OFF:关闭;
         * Banner.Mode.CONSOLE:控制台输出，默认方式;
         * Banner.Mode.LOG:日志输出方式;
         */
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}


	/**
	 * 使用代码注册Servlet不需要使用@ServletComponentScan注解
	 * 代码注册通过ServletRegistrationBean、 FilterRegistrationBean 和 ServletListenerRegistrationBean 获得控制。
	 * 也可以通过实现 ServletContextInitializer 接口直接注册。
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new Myservlet(),"/xs/*");
	}

	/**
	 * 修改DispatcherServlet默认配置
	 * @param dispatcherServlet
	 * @return
	 */
//	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.jsp");
		return registration;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize("3MB");
		// 设置总上传数据总大小
		factory.setMaxRequestSize("5MB");
		//Sets the directory location where files will be stored.
		//factory.setLocation("路径地址");
		return  factory.createMultipartConfig();
	}
}

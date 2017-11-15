package com.websocket.demo;

import com.websocket.demo.servlet.Myservlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ServletComponentScan
/**
 * 在 SpringBootApplication上使用@ServletComponentScan 注解后，
 * Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
 */
public class WebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
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
}

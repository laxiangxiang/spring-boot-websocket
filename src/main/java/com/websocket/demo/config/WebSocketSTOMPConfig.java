package com.websocket.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 目前主流浏览器的主流版本对WebSocket的支持都算是比较好的，
 * 但是在实际开发中使用WebSocket工作量会略大，而且增加了浏览器的兼容问题，
 * 这种时候我们更多的是使用WebSocket的一个子协议stomp，利用它来快速实现我们的功能
 * spring websocket利用STOMP作为websocket的子协议，原因是stomp可以提供一种类似springmvc的编码方式，
 * 可以利用注解进行接收和发送消息以及和springmvc进行无缝的结合
 */
@Configuration
@EnableWebSocketMessageBroker//表示开启使用STOMP协议来传输基于代理的消息
public class WebSocketSTOMPConfig extends AbstractWebSocketMessageBrokerConfigurer{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //endpointY就是websocket的端点，客户端需要注册这个端点进行链接，
        //setAllowedOrigins()方法表示允许连接的域名
        // withSockJS允许客户端利用sockjs进行浏览器兼容性处理
        //注册STOMP协议节点，同时指定使用SockJS协议
        stompEndpointRegistry.addEndpoint("endpointY").setAllowedOrigins("*").withSockJS();
    }


    /**
     * configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
     * registry.enableSimpleBroker("/topic", "/user");这句话表示在topic和user这两个域上可以向客户端发消息。
     * registry.setUserDestinationPrefix("/user");这句话表示给指定用户发送一对一的主题前缀是"/user"。
     * registry.setApplicationDestinationPrefixes("/app");这句话表示客户单向服务器端发送时的主题上面需要加"/app"作为前缀。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        super.configureMessageBroker(registry);
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/client");
        registry.setUserDestinationPrefix("/user");
    }
}

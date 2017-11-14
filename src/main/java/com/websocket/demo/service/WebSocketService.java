package com.websocket.demo.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 *
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
 * @author LXX
 */
@ServerEndpoint("/websocket")
@Component
public class WebSocketService {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketService> webSocketServices = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session sessions;

    /**
     * 连接建立成功调用的方法
     * 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param sessions
     */
    @OnOpen
    public void onOpen(Session sessions){
        this.sessions = sessions;
        webSocketServices.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为："+getOnlineCount());
    }

    /**
     * 连接关闭调用方法
     */
    @OnClose
    public void onClose(){
        webSocketServices.remove(this);
        subOnlineCount();
        System.out.println("有一连接个关闭！当前在线数为"+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选参数
     */
    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("来自客户端的消息："+message);
        //群发消息
        for (WebSocketService item : webSocketServices){
            try {
                item.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param sessions
     * @param error
     */
    @OnError
    public void onError(Session sessions,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException{
        this.sessions.getBasicRemote().sendText(message);
//        this.sessions.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketService.onlineCount ++;
    }

    public static synchronized void subOnlineCount(){
        WebSocketService.onlineCount --;
    }
}

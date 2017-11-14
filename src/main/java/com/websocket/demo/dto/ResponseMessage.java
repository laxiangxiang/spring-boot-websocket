package com.websocket.demo.dto;

/**
 * 服务器返回给浏览器的消息由这个类来承载
 */
public class ResponseMessage {
    private String responseMessage;

    public ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}

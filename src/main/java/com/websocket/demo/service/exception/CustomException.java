package com.websocket.demo.service.exception;

public class CustomException extends Exception{
    private Integer Code;

    public CustomException(String message, Integer code) {
        super(message);
        Code = code;
    }
    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }
}

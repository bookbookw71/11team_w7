package com.example.bookbookw71.controller.response;

import com.example.bookbookw71.model.StatusEnum;
import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;

    public Message(){
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

    public Message(Object obj){
        this.status = StatusEnum.OK;
        this.data = obj;
        this.message = null;
    }
}
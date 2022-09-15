package com.example.bookbookw71.controller.response;

import com.example.bookbookw71.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Message {
    private StatusEnum status;
    private int statusCode;
    private String message;
    private boolean check;//중복체크
    private Object data;

    public Message(String message,int statusCode){
        this.statusCode=statusCode;
        this.message=message;
    }

    public Message(Boolean check){
        this.check=check;
    }

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
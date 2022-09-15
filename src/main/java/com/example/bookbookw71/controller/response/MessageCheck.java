package com.example.bookbookw71.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class MessageCheck {
    private boolean check;//중복체크

    public MessageCheck(Boolean check){
        this.check=check;
    }

}

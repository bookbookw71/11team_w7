package com.example.bookbookw71.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    //private Long id;
    private String title;
    private String content;
    private String image_url;
    private int star;
    private int likenum;
}

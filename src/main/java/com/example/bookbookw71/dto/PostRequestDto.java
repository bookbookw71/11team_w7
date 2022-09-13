package com.example.bookbookw71.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequestDto {
    public int score;
    public String startTime;
    public String endTime ;
}

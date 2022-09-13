package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@AllArgsConstructor
public class CreatePost {

    private String title;

    private String content;

    private int bookPage;

    private int score;

    private String startTime;

    private String endTime;

    private Member username;
}

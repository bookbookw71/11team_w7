package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private long id;

    private String username;

    private String title;

    private String content;

    private String imageUrl;

    private int bookPage;

    private int star;

    private String startTime;

    private String endTime;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
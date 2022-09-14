package com.example.bookbookw71.service;

import javax.persistence.Column;

public class BookResponse {

    private String title;

    private String content;

    private String imageUrl;

    private int bookPage;

    private String author;

    public BookResponse(String title, String content, String imageUrl, int bookPage, String author) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.bookPage = bookPage;
        this.author = author;
    };


}

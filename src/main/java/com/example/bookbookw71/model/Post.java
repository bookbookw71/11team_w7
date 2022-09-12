package com.example.bookbookw71.model;

import com.example.bookbookw71.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int bookPage;

    @Column(nullable = false)
    private Long userId;

    public Post(PostRequestDto requestDto,Long userId){

        this.userId=userId;
        this.title=getTitle();
        this.content=getContent();
        this.bookPage=getBookPage();

    }


}

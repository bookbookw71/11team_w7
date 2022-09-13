package com.example.bookbookw71.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

//@Where(clause = "deleted_at IS NULL")
//@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int bookPage;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;


    public Post(String title, String content, String username, String imageUrl, int bookPage) {
        this.title = title;
        this.content =content;
        this.imageUrl = imageUrl;
        this.username = username;
        this.bookPage = bookPage;
    };

    public Post(String title, String username, String content, String imageUrl, int bookPage, int score, String startTime, String endTime) {
        this.title = title;
        this.content =content;
        this.username = username;
        this.imageUrl = imageUrl;
        this.bookPage = bookPage;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
    };

    public Post(int score, String startTime, String endTime) {
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
    };

}

package com.example.bookbookw71.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int bookPage;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String startTime = "";

    @Column(nullable = false)
    private String endTime = "";


    public Post(String title, Member username, int bookPage) {
        this.title = title;
        this.username = username;
        this.bookPage = bookPage;
    };

    public Post(String title, Member username, int bookPage, int score, String startTime, String endTime) {
        this.title = title;
        this.username = username;
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

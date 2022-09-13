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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

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


    public Post(String title, String username, int bookPage) {
        this.title = title;
        this.username = username;
        this.bookPage = bookPage;
    };
}

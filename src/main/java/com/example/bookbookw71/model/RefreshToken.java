package com.example.bookbookw71.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken extends Timestamped {

    @Id
    @Column(nullable = false)
    private String kkey;


    @Column(nullable = false)
    private String vvalue;

    @Builder
    public RefreshToken(String key,String value){
        this.kkey=key;
        this.vvalue=value;
    }

}
package com.example.bookbookw71;

import com.example.bookbookw71.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bookbookw71Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Bookbookw71Application.class, args);
        SearchService.printSearch("bts");
    }

}

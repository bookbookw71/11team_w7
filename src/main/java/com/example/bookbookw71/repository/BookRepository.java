package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

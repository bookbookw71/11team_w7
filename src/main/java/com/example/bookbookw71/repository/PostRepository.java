package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public abstract class PostRepository implements JpaRepository<Post, Long> {

    @Query("select post from Post post join fetch post.username where post.id=:id")
    abstract Optional<Post> get(Long id);

    @Query("select post from Post post join fetch post.username")
    abstract List<Post> getAll();
}

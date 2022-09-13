package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{

    @Query("select post from Post post join fetch post.username where post.id=:id")
    Optional<Post> get(Long id);

    @Query("select post from Post post join fetch post.username")
    List<Post> getAll();
}


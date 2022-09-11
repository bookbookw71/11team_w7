package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Post;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class PostRepository extends JpaRepository<Post,Long>{
    List<Post> findAllByUserId(Long userId);
}

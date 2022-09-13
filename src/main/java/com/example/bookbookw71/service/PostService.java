package com.example.bookbookw71.service;

import com.example.bookbookw71.dto.CreatePost;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(CreatePost create) {
        Post post = new Post(create.getTitle(), create.getUsername(), create.getBookPage(), create.getScore(), create.getStartTime(), create.getEndTime());

        return postRepository.save(post);
    }

}

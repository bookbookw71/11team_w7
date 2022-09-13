package com.example.bookbookw71.service;

import com.example.bookbookw71.dto.CreatePost;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(CreatePost create) {
        Post post = new Post(create.getTitle(), create.getUsername(), create.getImageUrl(), create.getBookPage(), create.getScore(), create.getStartTime(), create.getEndTime());

        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getById(Long id) throws NotFoundException {
        Post post = postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        return post;
    }

    public void deleteById(Long id) throws NotFoundException {
        postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        postRepository.deleteById(id);
    }

    //TODO: post 수정기능 구현 해야 함.

}
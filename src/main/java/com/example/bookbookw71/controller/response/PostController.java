package com.example.bookbookw71.controller.response;

import com.example.bookbookw71.repository.PostRepository;
import com.example.bookbookw71.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final PostlikeRepository postlikeRepository;
    @PostMapping("/api/moviepost")
    public ResponseEntity<Message> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) throws Exception {
        return postService.createPost(requestDto, request);
    }
    @GetMapping("/api/main")
    public ResponseDto<?> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("/api/moviepost/{postId}")
    public PostResponseDto getOnePost(@PathVariable Long postId) {
        return postService.getOnePost(postId);
    }

    @DeleteMapping("/api/moviepost/{postId}")
    public ResponseEntity<Message> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePost(postId, request);
    }
    @PutMapping("/api/moviepost/{postId}")
    public ResponseEntity<Message> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, HttpServletRequest request) throws Exception {
        return postService.updatePost(postId, requestDto, request);
    }
}
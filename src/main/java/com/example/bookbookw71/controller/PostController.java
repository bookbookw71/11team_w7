package com.example.bookbookw71.controller;

import com.example.bookbookw71.controller.response.ResponseDto;
import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.repository.BookRepository;
import com.example.bookbookw71.repository.MemberRepository;
import com.example.bookbookw71.search.SearchService;
import com.example.bookbookw71.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

    public final PostService postService;
    public final SearchService searchService;
    public final MemberRepository memberRepository;
    public final BookRepository bookRepository;


    @PostMapping("/api/auth/post")
    public ResponseDto<?> postCreate(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        System.out.println("포스트 컨트롤러");
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/api/auth/post")
    public ResponseDto<?> getAllPost(HttpServletRequest request) {
        return postService.getAllPost(request);
    }


    @GetMapping("/api/auth/post/{postId}")
    public ResponseDto<?> getOnePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.getOnePost(postId, request);
    }

    @DeleteMapping("/api/auth/post/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePost(postId,request);
    }

    @PutMapping("/api/auth/post/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

}
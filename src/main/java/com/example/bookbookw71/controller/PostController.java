package com.example.bookbookw71.controller;

import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.service.MemberDetailsImpl;
import com.example.bookbookw71.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController //Json으로 데이터 주고받음
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }

    //신규 포스트 등록
    @PostMapping("/api/auth/post")
    public Post createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){ // @AuthenticationPrincipal MemberDetailsImpl: 로그인에 성공한 사용자의 정보가 넘어옴
        //현재 로그인 되어있는 회원 테이블의 ID
        Long userId=memberDetails.getMember().getId();
        Post post=postService.createPost(requestDto,userId); //서비스에서 post등록 처리

        return post;

    }

    //설정 변경
    @PutMapping("/api/auth/post/{postId}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        Post post = postService.updatePost(id,requestDto);

        return post.getUserId();
    }

    //로그인한 본인이 적은 포스트만 조회 //
    @GetMapping("/")
    public List<Post> getPosts(@AuthenticationPrincipal MemberDetailsImpl memberDetails){
        Long userId=memberDetails.getMember().getId();
        return postService.getPost(userId);
    }
}

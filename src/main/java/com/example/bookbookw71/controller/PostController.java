package com.example.bookbookw71.controller;

import com.example.bookbookw71.API.SearchService;
import com.example.bookbookw71.dto.CreatePost;
import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.dto.PostResponse;
import com.example.bookbookw71.model.Book;
import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.BookRepository;
import com.example.bookbookw71.repository.MemberRepository;
import com.example.bookbookw71.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "posts", description = "게시글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    public final PostService postService;
    public final SearchService searchService;
    public final MemberRepository memberRepository;
    public final BookRepository bookRepository; // TODO: 책 검색 기능 개발 후 삭제할 것

    @Operation(summary = "create posts", description = "게시글 생성")
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequestDto requestDto) {


        // TODO: 인증 기능 개발 후 삭제할 것
        Member writer = new Member("버블티", "bubble@bubble.com", "password");
        memberRepository.save(writer);
        // TODO: 책 검색 기능 개발 후 삭제할 것

        Book book = new Book("제목", "책 소개", 613);
        bookRepository.save(book);

        CreatePost create = new CreatePost(book.getTitle(), book.getContent(), book.getBookPage(), requestDto.getScore(),requestDto.getStartTime(), requestDto.endTime, writer);

        Post post = postService.create(create);

        PostResponse response = getPostResponse(post, book);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    private PostResponse getPostResponse(Post post, Book book) {
        return PostResponse.builder()
                .id(post.getId())
                .title(book.getTitle())
                .content(book.getContent())
                .bookPage(book.getBookPage())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .username(post.getUsername())
                .build();
    }
}

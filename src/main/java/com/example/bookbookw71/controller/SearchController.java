package com.example.bookbookw71.controller;

import com.example.bookbookw71.API.SearchService;
import com.example.bookbookw71.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "search", description = "검색 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    public final PostService postService;
    public final SearchService searchService;


    //public final AuthorRepository  authorRepository; // TODO: 인증 기능 개발 후 삭제할 것



}

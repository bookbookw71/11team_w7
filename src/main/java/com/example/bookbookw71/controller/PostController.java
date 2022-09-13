package com.example.bookbookw71.controller;

import com.example.bookbookw71.controller.response.ResponseDto;
import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.repository.BookRepository;
import com.example.bookbookw71.repository.MemberRepository;
import com.example.bookbookw71.search.SearchService;
import com.example.bookbookw71.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    public final PostService postService;
    public final SearchService searchService;
    public final MemberRepository memberRepository;
    public final BookRepository bookRepository;


    @RequestMapping(value = "/api/post", method = RequestMethod.POST)
    public ResponseDto<?> postCreate(@RequestBody PostRequestDto requestDto) {
        System.out.println("포스트 컨트롤러");
        return postService.createPost(requestDto);
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.GET)
    public ResponseDto<?> getAllPost() {
        return postService.getAllPost();
    }




}












//
////@RequestMapping("/api/posts")
//@Slf4j
//@Tag(name = "posts", description = "게시글 API")
//@RequiredArgsConstructor
//@RestController
//public class PostController {
//
//    public final PostService postService;
//    public final SearchService searchService;
//    public final MemberRepository memberRepository;
//    public final BookRepository bookRepository; // TODO: 책 검색 기능 개발 후 삭제할 것
//
////    @Operation(summary = "create posts", description = "게시글 생성")
////    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = PostResponse.class)))
////    @PostMapping("")
//    @ResponseBody
//    @RequestMapping(value = "/api/posts", method = RequestMethod.POST)
//    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequestDto requestDto) {
//        System.out.println("createPost 시작");
//
//        // TODO: 인증 기능 개발 후 삭제할 것
//        Member writer = new Member("닉네임", "emaill12@bubble.com", "password");
//        memberRepository.save(writer);
//
//        // TODO: 책 검색 기능 개발 후 삭제할 것
//        Book book = new Book("제목", "책 소개","이미지url", 309);
//        bookRepository.save(book);
//
//        CreatePost create = new CreatePost(book.getTitle(), book.getContent(), book.getImageUrl(), book.getBookPage(), requestDto.getScore(),requestDto.getStartTime(), requestDto.getEndTime(), writer);
//        Post post = postService.create(create);
//
//        PostResponse response = getPostResponse(post);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @Operation(summary = "get posts", description = "게시글들 가져오기")
//    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostResponse.class)))
//    @ResponseBody
//    @GetMapping("")
//    public ResponseEntity<List<PostResponse>> getPosts() {
//        List<Post> posts = postService.getAll();
//
//        List<PostResponse> response = new ArrayList<>();
//
//        for (Post post : posts) {
//            PostResponse tmp = getPostResponse(post);
//            response.add(tmp);
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @Operation(summary = "get post", description = "게시글 상세 조회")
//    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostResponse.class)))
//    @ResponseBody
//    @GetMapping("/{id}")
//    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException {
//
//        Post post = postService.getById(id);
//
//        PostResponse response = getPostResponse(post);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "update posts", description = "게시글 삭제하기")
//    @ApiResponse(responseCode = "200", description = "OK")
//    @ResponseBody
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deletePostById(@PathVariable Long id) throws NotFoundException {
//        postService.deleteById(id);
//        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    private PostResponse getPostResponse(Post post) {
//        return PostResponse.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .content(post.getContent())
//                .bookPage(post.getBookPage())
//                .startTime(post.getStartTime())
//                .endTime(post.getEndTime())
//                .createdAt(post.getCreatedAt())
//                .modifiedAt(post.getModifiedAt())
//                .username(post.getUsername())
//                .build();
//    }
//}

package com.example.bookbookw71.service;

import com.example.bookbookw71.controller.response.ResponseDto;
import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.dto.PostResponse;
import com.example.bookbookw71.jwt.TokenProvider;
import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.MemberRepository;
import com.example.bookbookw71.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletResponse response) {
        Member member = tokenProvider.getMemberFromAuthentication();
        //TODO: fail인 경우 고려

        Post post = new Post(requestDto.getTitle(), member.getUsername(), requestDto.getContent(), requestDto.getImageUrl(), requestDto.getBookPage(), requestDto.getStar(), requestDto.getReadStart(), requestDto.getReadEnd());
        postRepository.save(post);

        return ResponseDto.success(
                PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(member.getUsername())
                        .imageUrl(post.getImageUrl())
                        .bookPage(post.getBookPage())
                        .star(post.getStar())
                        .readStart(post.getReadStart())
                        .readEnd(post.getReadEnd())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    public ResponseDto<?> getAllPost(){
        List<Post> postList = postRepository.findAll();
        List<PostResponse> responseDtos = new ArrayList<>();

        for(Post post:postList){
            responseDtos.add(PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .username(post.getUsername())
                    .imageUrl(post.getImageUrl())
                    .bookPage(post.getBookPage())
                    .star(post.getStar())
                    .readStart(post.getReadStart())
                    .readEnd(post.getReadEnd())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
            );
        }
        return ResponseDto.success(responseDtos);
    }

    public ResponseDto<?> getOnePost(Long postId) {
        Optional<Post> optionalPost=postRepository.findById(postId);
        Post post=optionalPost.get();

        return ResponseDto.success(
                PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(post.getUsername())
                        .imageUrl(post.getImageUrl())
                        .bookPage(post.getBookPage())
                        .star(post.getStar())
                        .readStart(post.getReadStart())
                        .readEnd(post.getReadEnd())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    public ResponseDto<?> deletePost(Long postId, HttpServletResponse response){
        Member member = tokenProvider.getMemberFromAuthentication();
        //작성자 확인, 토큰확인, 존재하는 게시글 여부 확인 필요함
//        Optional<Post> optionalPost=postRepository.findById(postId);
//        Post post=optionalPost.get();

        System.out.println("포스트 지우기 시도");
        postRepository.deleteById(postId);
        String massage = ("포스트가 삭제되었습니다."+postId);
        System.out.println(massage);
        return ResponseDto.success(massage);
    }


    //TODO: post 수정기능 구현 해야 함.
    public ResponseDto<?> updatePost(Long postId, PostRequestDto requestDto, HttpServletResponse response){
        Member member = tokenProvider.getMemberFromAuthentication();
        Post post = isPresentPost(postId);
        post.update(requestDto);
        postRepository.save(post);

        return ResponseDto.success(post);
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

}
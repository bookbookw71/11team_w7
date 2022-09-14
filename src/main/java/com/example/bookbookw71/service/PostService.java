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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        //TODO: requestDto 값이 부족할 때(null)
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member){
            return ResponseDto.fail("MEMBER_NOT_FOUND","인증된 멤버정보가 없습니다.");
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);
            return ResponseDto.fail("INVALID_TOKEN","Refresh-Token이 없습니다");
        }

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

    public ResponseDto<?> getAllPost(HttpServletRequest request){
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member){
            return ResponseDto.fail("MEMBER_NOT_FOUND","인증된 멤버정보가 없습니다.");
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);//이거 왜 넣는건지 모름
            return ResponseDto.fail("INVALID_TOKEN","Refresh-Token이 없습니다");
        }

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

    public ResponseDto<?> getOnePost(Long postId, HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Post posts = isPresentPost(postId);
        if (null == member){
            return ResponseDto.fail("MEMBER_NOT_FOUND","인증된 멤버정보가 없습니다.");
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);//이거 왜 넣는건지 모름
            return ResponseDto.fail("INVALID_TOKEN","Refresh-Token이 없습니다");
        }
        if (null == posts){
            return ResponseDto.fail("POST_NOT_FOUND","게시글이 존재하지 않습니다.");
        }

        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.get();

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

    public ResponseDto<?> deletePost(Long postId, HttpServletRequest request){
        Member member = tokenProvider.getMemberFromAuthentication();
        Post post = isPresentPost(postId);
        if (null == member){
            return ResponseDto.fail("MEMBER_NOT_FOUND","인증된 멤버정보가 없습니다.");
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);
            return ResponseDto.fail("INVALID_TOKEN","Refresh-Token이 없습니다");
        }
        if (null == post){
            return ResponseDto.fail("POST_NOT_FOUND","게시글이 존재하지 않습니다.");
        }
        if(true==post.validateMember(member)){
            return ResponseDto.fail("NOT_AUTHORITY","작성자만 수정할 수 있습니다.");
        }


        System.out.println("포스트 지우기 시도");
        postRepository.deleteById(postId);
        String massage = ("포스트가 삭제되었습니다."+postId);
        System.out.println(massage);
        return ResponseDto.success(massage);
    }


    public ResponseDto<?> updatePost(Long postId, PostRequestDto requestDto, HttpServletRequest request){
        //TODO: requestDto 값이 부족할 때(null)
        Member member = tokenProvider.getMemberFromAuthentication();
        Post post = isPresentPost(postId);
        if (null == member){
            return ResponseDto.fail("MEMBER_NOT_FOUND","인증된 멤버정보가 없습니다.");
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);
            return ResponseDto.fail("INVALID_TOKEN","Refresh-Token이 없습니다");
        }
        if (null == post){
            return ResponseDto.fail("POST_NOT_FOUND","게시글이 존재하지 않습니다.");
        }
        if(true==post.validateMember(member)){
            return ResponseDto.fail("NOT_AUTHORITY","작성자만 수정할 수 있습니다.");
        }


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
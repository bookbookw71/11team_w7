package com.example.bookbookw71.service;

import com.example.bookbookw71.controller.response.ResponseDto;
import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.dto.PostResponse;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    @Transactional
    public ResponseDto<?> create(PostRequestDto requestDto) {
        System.out.println("postservice에서 시작 되는지");

        Post post = new Post(requestDto.getTitle(), requestDto.getUsername(), requestDto.getContent(), requestDto.getImageUrl(), requestDto.getBookPage(), requestDto.getScore(), requestDto.getStartTime(), requestDto.getEndTime());
        postRepository.save(post);
        System.out.println("postservice에서 return 직전");
        return ResponseDto.success(
                PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(post.getUsername())
                        .imageUrl(post.getImageUrl())
                        .bookPage(post.getBookPage())
                        .score(post.getScore())
                        .startTime(post.getStartTime())
                        .endTime(post.getEndTime())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }


//
//
//    //아래 부분은 그냥 response 바디에 안 들어가는 형태
//    public List<Post> getAll() {
//        return postRepository.findAll();
//    }
//
//    public Post getById(Long id) throws NotFoundException {
//        Post post = postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
//        return post;
//    }
//
//    public void deleteById(Long id) throws NotFoundException {
//        postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
//        postRepository.deleteById(id);
//    }

    //TODO: post 수정기능 구현 해야 함.

}
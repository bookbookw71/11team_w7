package com.example.bookbookw71.service;

import com.example.bookbookw71.dto.PostRequestDto;
import com.example.bookbookw71.model.Post;
import com.example.bookbookw71.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository=postRepository;
    }


    public Post createPost(PostRequestDto requestDto,Long userId){
        Post post = new Post(requestDto,userId);
        postRepository.save(post);
        return post;
    }

    //회원id로 등록된 post조회
    public List<Post> getPost(Long userId){
        return postRepository.findAllByUserId(userId);
    }

    public Post updatePost(PostRequestDto requestDto, Long id){
        Post post =postRepository.findById(id)
                        .orElseThrow(()->new NullPointerException("해당 아이디가 존재하지 않음."));
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setBookPage(requestDto.getBookPage());
        postRepository.save(post);
        return post;
    }

}

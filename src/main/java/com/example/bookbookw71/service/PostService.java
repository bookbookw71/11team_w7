package com.week06.team01.service;

import com.week06.team01.controller.request.PostRequestDto;
import com.week06.team01.controller.response.Message;
import com.week06.team01.controller.response.PostResponseDto;
import com.week06.team01.controller.response.ResponseDto;
import com.week06.team01.controller.response.StatusEnum;
import com.week06.team01.domain.Heart;
import com.week06.team01.domain.Member;
import com.week06.team01.domain.Post;
import com.week06.team01.jwt.TokenProvider;
import com.week06.team01.repository.HeartRepository;
import com.week06.team01.repository.MemberRepository;
import com.week06.team01.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public ResponseEntity<Message> createPost(PostRequestDto requestDto, HttpServletRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        if (null == request.getHeader("Authorization")) {
            Message message = new Message();
            message.setStatus(StatusEnum.LOGIN_REQUIRED);
            message.setMessage("로그인이 필요합니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        Member member = validateMember(request);
        if (null == member) {
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_TOKEN);
            message.setMessage("Token이 유효하지 않습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        try{
            Post post = Post.builder()
                    .member(member)
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .likenum(requestDto.getLikenum())
                    .image_url(crawling(requestDto))
                    .star(requestDto.getStar())
                    .build();
            postRepository.save(post);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            Message message = new Message(postResponseDto);
            return new ResponseEntity<>(message,headers,HttpStatus.OK);
        }catch (Exception e){
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_URL);
            message.setMessage("url형식이 올바르지 않습니다");
            return new ResponseEntity<>(message,headers, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseDto<List<PostResponseDto>> getAllPost(){
        List<Post> postList = postRepository.findAllByOrderByCreatedAtAsc();
        List<PostResponseDto> responseDtos = new ArrayList<>();
        for(Post post:postList){
            int cntHeart = heartRepository.countHeartByPostId(post.getId());
            responseDtos.add(PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .image_url(post.getImage_url())
                    .star(post.getStar())
                    .likenum(post.getLikenum())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
            );
        }
        return ResponseDto.success(responseDtos);
    }

    public PostResponseDto getOnePost(Long postId) {
        Optional<Post> optionalPost=postRepository.findById(postId);
        Post post=optionalPost.get();
        int cntHeart = heartRepository.countHeartByPostId(post.getId());
        return  PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image_url(post.getImage_url())
                .star(post.getStar())
                .likenum(post.getLikenum())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    public ResponseEntity<Message> updatePost(Long postId, PostRequestDto requestDto,HttpServletRequest request) throws Exception {
        Member member = validateMember(request);
        HttpHeaders headers = new HttpHeaders();
        if (null == member) {
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_TOKEN);
            message.setMessage("Token이 유효하지 않습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);

        }
        Optional<Post> optionalPost=postRepository.findById(postId);
        Post post =optionalPost.get();

        if(true==post.validateMember(member)){
            Message message = new Message();
            message.setStatus(StatusEnum.NOT_AUTHORITY);
            message.setMessage("작성자만 수정할 수 있습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        post.update(requestDto);
        post.setMember(member);
        post.setImage_url(crawling(requestDto));
        postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        Message message = new Message(postResponseDto);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    public ResponseEntity<Message> deletePost(Long postId,HttpServletRequest request){
        Member member = validateMember(request);
        HttpHeaders headers = new HttpHeaders();
        if (null == member) {
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_TOKEN);
            message.setMessage("Token이 유효하지 않습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }

        Optional<Post> optionalPost=postRepository.findById(postId);
        Post post=optionalPost.get();

        if(true==post.validateMember(member)){
            Message message = new Message();
            message.setStatus(StatusEnum.NOT_AUTHORITY);
            message.setMessage("작성자만 삭제할 수 있습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        postRepository.delete(post);
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        String temp = request.getHeader("Authorization").substring(7);
        if (!tokenProvider.validateToken(temp)) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    public String crawling(PostRequestDto requestDto) throws Exception {
        String imgUrl = requestDto.getImage_url();
        Connection conn = Jsoup.connect(imgUrl).ignoreContentType(true);
        // #content > div.article > div.mv_info_area > div.poster > a > img
        //#content > div.article > div.wide_info_area > div.poster > a > img
        Document document = conn.get();
        Elements elements = document.select("div.poster a img");
        return String.valueOf(elements.attr("src"));
    }
    @Transactional
    public ResponseDto<?> clickHeart(Long postId, HttpServletRequest request) {
        Member member = memberRepository.getReferenceById(1L);

        Heart isDoneHeart = heartRepository.findByPostIdAndMemberId(postId, member.getId());

        if (null != isDoneHeart) {
            heartRepository.delete(isDoneHeart);
            return ResponseDto.success("좋아요 취소 성공!");
        }

        Heart heart = Heart.builder()
                .member(member)
                .post(isPresentPost(postId))
                .build();

        heartRepository.save(heart);

        return ResponseDto.success("좋아요 성공!");
    }

    private Post isPresentPost(Long postId) {
        Optional<Post> optionalPost=postRepository.findById(postId);
        return optionalPost.orElse(null);

    }}


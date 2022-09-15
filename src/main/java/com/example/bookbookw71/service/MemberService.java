package com.example.bookbookw71.service;

import com.example.bookbookw71.controller.request.*;
import com.example.bookbookw71.controller.response.Message;
import com.example.bookbookw71.dto.MemberResponseDto;
import com.example.bookbookw71.jwt.TokenProvider;
import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.model.MemberRoleEnum;
import com.example.bookbookw71.model.StatusEnum;
import com.example.bookbookw71.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<Message> createMember(MemberSignDto memberSignDto) {
        HttpHeaders headers = new HttpHeaders();
//        if (!requestDto.getPassword().equals(requestDto.getPwdCheck())) {
//            Message message = new Message();
//            message.setStatus(StatusEnum.PASSWORDS_NOT_MATCHED);
//            message.setMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
//            return new ResponseEntity<>(message,headers, HttpStatus.BAD_REQUEST);
//        }
        Member member = Member.builder()
                .username(memberSignDto.getUsername())
                .email(memberSignDto.getEmail())
                .password(passwordEncoder.encode(memberSignDto.getPassword()))
                .role(MemberRoleEnum.MEMBER)
                .build();
        memberRepository.save(member);
        Message message = new Message("회원가입 성공",200);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> login(MemberLoginDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getEmail());
        HttpHeaders headers = new HttpHeaders();

        if (null == member) {
            Message message = new Message();
            message.setStatus(StatusEnum.MEMBER_NOT_FOUND);
            message.setMessage("사용자를 찾을 수 없습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_MEMBER);
            message.setMessage("사용자를 찾을 수 없습니다.");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);
        Message message = new Message(MemberResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build()
        );
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }


    public ResponseEntity<Message> logout(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromAuthentication();
        HttpHeaders headers = new HttpHeaders();
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            tokenProvider.deleteRefreshToken(member);
            Message message = new Message();
            message.setStatus(StatusEnum.INVALID_TOKEN);
            message.setMessage("Refresh-Token이 없습니다");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        if (null == member) {
            Message message = new Message();
            message.setStatus(StatusEnum.MEMBER_NOT_FOUND);
            message.setMessage("인증된 멤버정보가 없습니다");
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }

        Message message = new Message(MemberResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .build());
        message.setMessage("Success");
        message.setStatus(StatusEnum.OK);
        tokenProvider.deleteRefreshToken(member);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

//    public ResponseEntity<Message> info(HttpServletRequest request) {
//        Member member = postService.validateMember(request);
//        Message message = new Message(MemberResponseDto.builder()
//                .id(member.getId())
//                //.nickname(member.getNickname())
//                .username(member.getUsername()).build());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        return new ResponseEntity<>(message,headers,HttpStatus.OK);
//    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Transactional
    public ResponseEntity<Message> idCheck(MemberCheckDto memberCheckDto) {
        Optional<Member> member = memberRepository.findByUsername(memberCheckDto.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        if(member.isPresent()){
            Message message=new Message(false);
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        else{
            Message message=new Message(true);
            return new ResponseEntity<>(message,headers,HttpStatus.OK);
        }
    }

    public ResponseEntity<Message> emailCheck(MemberCheckDto memberCheckDto) {
        Optional<Member> member = memberRepository.findByEmail(memberCheckDto.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        if(member.isPresent()){
            Message message=new Message(false);
            return new ResponseEntity<>(message,headers,HttpStatus.BAD_REQUEST);
        }
        else{
            Message message=new Message(true);
            return new ResponseEntity<>(message,headers,HttpStatus.OK);
        }
    }

}


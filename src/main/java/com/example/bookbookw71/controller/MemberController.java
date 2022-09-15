package com.example.bookbookw71.controller;

import com.example.bookbookw71.controller.request.MemberLoginDto;
import com.example.bookbookw71.controller.request.MemberCheckDto;
import com.example.bookbookw71.controller.request.MemberSignDto;
import com.example.bookbookw71.controller.response.Message;
import com.example.bookbookw71.oauth.model.OauthResponseModel;
import com.example.bookbookw71.oauth.service.GoogleService;
import com.example.bookbookw71.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private final GoogleService googleService;

    //닉네임 중복검사
    @PostMapping("/api/member/nickname")
    public ResponseEntity<Message> idCheck(@RequestBody @Valid MemberCheckDto memberCheckDto) {
        return memberService.idCheck(memberCheckDto);
    }

    //이메일 중복검사
    @PostMapping("/api/member/email")
    public ResponseEntity<Message> emailCheck(@RequestBody @Valid MemberCheckDto memberCheckDto){
        return memberService.emailCheck(memberCheckDto);
    }

    //회원가입
    @PostMapping("/api/member/signup")
    public ResponseEntity<Message> signup(@RequestBody @Valid MemberSignDto memberSignDto) {
        return memberService.createMember(memberSignDto);
    }

    //로그인
    @PostMapping("/api/member/login")
    public ResponseEntity<Message> login(@RequestBody @Valid MemberLoginDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

//    //카카오로그인 인증코드
//    @RequestMapping(value = "/api/member/kakaologin", method = RequestMethod.GET)
//    //Access-Token 요청
//    @RequestMapping(value = "/api/member/kakaologin", method = RequestMethod.GET)

    //로그아웃
    @PostMapping("/api/auth/member/logout")
    public ResponseEntity<Message> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }
//
//    @RequestMapping(value = "/api/auth/member/info", method = RequestMethod.GET)
//    public ResponseEntity<Message> info(HttpServletRequest request){return memberService.info(request);}


    /**
     * 구글 로그인
     */
    @GetMapping("/login/oauth2/code/{oauth}")
    public ResponseEntity<OauthResponseModel> OauthLogin(@RequestParam(name = "code") String code, HttpServletResponse response,
                                                         @RequestParam(value = "state", required = false) String state , @PathVariable String oauth) throws JsonProcessingException {
        if(oauth.equals("google"))
            return googleService.oauthLogin(code, response);
//        else if (oauth.equals("kakao")) {
//            return googleService.kakaologin(code, response);
//        }


        OauthResponseModel oauthResponseModel = OauthResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("해당하는 소셜 로그인 정보가 없음").build();
        //////수정
        return new ResponseEntity<>(oauthResponseModel, oauthResponseModel.getHttpStatus());
    }

}


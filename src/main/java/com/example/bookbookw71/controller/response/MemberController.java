package com.example.bookbookw71.controller.response;

import com.example.bookbookw71.controller.request.LoginRequestDto;
import com.example.bookbookw71.controller.request.MemberRequestDto;
import com.example.bookbookw71.controller.request.MemberSignDto;
import com.example.bookbookw71.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final com.week06.team01.service.MemberService memberService;

    @RequestMapping(value = "/api/member/signup/check", method = RequestMethod.POST)
    public ResponseEntity<Message> idCheck(@RequestBody @Valid MemberSignDto memberSignDto) {
        return memberService.idCheck(memberSignDto);
    }

    @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
    public ResponseEntity<Message> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
    public ResponseEntity<Message> login(@RequestBody @Valid LoginRequestDto requestDto,
                                         HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }


    @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
    public ResponseEntity<Message> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    @RequestMapping(value = "/api/auth/member/info", method = RequestMethod.GET)
    public ResponseEntity<Message> info(HttpServletRequest request){return memberService.info(request);}
}


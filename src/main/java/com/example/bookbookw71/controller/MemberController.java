package com.example.bookbookw71.controller;

import com.example.bookbookw71.dto.SignupRequestDto;
import com.example.bookbookw71.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //로그인
    @GetMapping("/api/member/login")
    public String login() {
        return "login";
    }

    @GetMapping("/api/member/register")
    public String signup() {
        return "signup";
    }

    //회원가입 요청
    @PostMapping("/api/member/register")
    public String registerMember(SignupRequestDto requestDto) {
        memberService.registerMember(requestDto);
        return "redirect:/api/member/login";
}
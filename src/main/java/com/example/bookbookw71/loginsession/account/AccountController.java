package com.example.bookbookw71.loginsession.account;


import com.example.bookbookw71.loginsession.account.dto.AccountReqDto;
import com.example.bookbookw71.loginsession.account.dto.LoginReqDot;
import com.example.bookbookw71.loginsession.security.user.UserDetailsImpl;
import com.example.bookbookw71.loginsession.jwt.util.JwtUtil;
import com.example.bookbookw71.oauth.service.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final KakaoService kakaoService;

    @PostMapping("/account/signup")
    public GlobalResDto signup(@RequestBody @Valid AccountReqDto accountReqDto) {
        return accountService.signup(accountReqDto);
    }

    @PostMapping("/account/login")
    public GlobalResDto login(@RequestBody @Valid LoginReqDot loginReqDot, HttpServletResponse response) {
        return accountService.login(loginReqDot, response);
    }

    @GetMapping("/issue/token")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getAccount().getEmail(), "Access"));
        return new GlobalResDto("issuedToken Success", HttpStatus.OK.value());
    }

    @GetMapping("/account/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoService.kakaoLogin(code, response);

        return "redirect:/";
    }

}

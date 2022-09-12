package com.example.bookbookw71.loginsession.account.service;

import com.example.bookbookw71.loginsession.account.dto.AccountReqDto;
import com.example.bookbookw71.loginsession.account.dto.LoginReqDot;

import javax.servlet.http.HttpServletResponse;

public interface AccountService {
    GlobalResDto signup(AccountReqDto accountReqDto);

    GlobalResDto login(LoginReqDot loginReqDot, HttpServletResponse response);
}


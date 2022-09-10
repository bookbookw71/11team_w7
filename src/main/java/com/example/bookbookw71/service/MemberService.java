package com.example.bookbookw71.service;

import com.example.bookbookw71.dto.SignupRequestDto;
import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.model.MemberRoleEnum;
import com.example.bookbookw71.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void registerMember(SignupRequestDto requestDto) {
        String membername = requestDto.getMembername();
        String password = requestDto.getPassword();
        // 회원 ID 중복 확인
        Optional<Member> found = memberRepository.findByMembername(membername);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        String email = requestDto.getEmail();
//        // 사용자 ROLE 확인
//        MemberRoleEnum role = MemberRoleEnum.MEMBER;
//        if (requestDto.isAdmin()) {
//            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = MemberRoleEnum.ADMIN;
//        }

        Member member = new Member(membername, password, email, role);
        memberRepository.save(member);
    }
}

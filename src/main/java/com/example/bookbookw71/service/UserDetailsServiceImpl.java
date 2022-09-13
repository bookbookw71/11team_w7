package com.example.bookbookw71.service;

import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.model.UserDetailsImpl;
import com.example.bookbookw71.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("NOT_FOUND_MEMBER")
        );
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setMember(member);
        return userDetails;
    }
}
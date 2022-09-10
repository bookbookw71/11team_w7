package com.example.bookbookw71.service;

import com.example.bookbookw71.model.Member;
import com.example.bookbookw71.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + nickname));

        return new MemberDetailsImpl(member);
    }
}
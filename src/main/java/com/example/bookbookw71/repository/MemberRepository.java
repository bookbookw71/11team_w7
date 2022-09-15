package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    //Optional<Member> findByKakaoId(Long kakaoId);


    Member findMemberByEmail(String email);
}
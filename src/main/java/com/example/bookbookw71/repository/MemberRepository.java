package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
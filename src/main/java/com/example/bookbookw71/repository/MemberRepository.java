package com.example.bookbookw71.repository;

import com.example.bookbookw71.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepository extends JpaRepository<Member, Long> {
}

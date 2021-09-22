package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    // Optional<>로 하면 null-safe (Java 8 추가 기능)
    List<Member> findAll();
}

package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // ** tip: interface는 다중 상속이 가능 !!
    // Spring Data Jpa가 자동으로 구현체도 만들어서 자동으로 Spring bean에 등록해준다.

    // Spring Data Jpa는 공통 CRUD는 제공 (CrudRepository, PagingAndSortingRepository 등)
    // 개별 비즈니스 로직에 필요한 부분만 아래처럼 method만 작성하면 자동으로 쿼리 생성
    // 규칙 : findBy(Sth), findBy(Sth)And(Sth) 등 interface method 명으로 자동 생성 !!!
    // ** reflection 기술 적용 ??

    @Override
    Optional<Member> findByName(String name);
}

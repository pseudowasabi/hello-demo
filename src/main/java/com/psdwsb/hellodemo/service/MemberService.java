package com.psdwsb.hellodemo.service;

import com.psdwsb.hellodemo.domain.Member;
import com.psdwsb.hellodemo.repository.MemberRepository;
import com.psdwsb.hellodemo.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /* 회원 가입 */
    public Long join(Member member) {
        // * 조건 : 동명의 회원 가입 방지
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    /* 동명의 회원 가입 방지 */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 이름의 회원입니다.");
                        });
        // .get()으로 바로 꺼내는 것 권장 X, Optional<>로 감싸기 때문에 ifPresent() 사용 가능
        // .orElseGet() 같은 method를 이용하는 경우 있음 (참고)
    }

    /* 전체 회원 검색 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* Id를 통해 한 명의 회원 검색 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

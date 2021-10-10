package com.psdwsb.hellodemo.service;

import com.psdwsb.hellodemo.domain.Member;
import com.psdwsb.hellodemo.repository.MemberRepository;
import com.psdwsb.hellodemo.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
// javax가 아닌 springframework의 annotation을 이용해야 함 (@Transactional)
// JPA를 사용할 때 Service 계층에도 이 annotation이 있어야 함 (회원가입-join에 필요)
public class MemberService {
    /* Service는 비즈니스 로직에 관련한 것, Repository는 데이터를 다루는 입장에서 보기 !
     * 유사한 내용이 들어오게 되더라도, 이렇게 나누어야 나중에 기획에서 요구사항이 변경되어도 로직 변경이 용이해짐 */
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        // MemberService class 입장에서 보면, MemberRepository가 외부에서 주입된다.
        // = Dependency Injection (DI)
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(Member member) {
        // * 설계 요구 조건 : 동명의 회원 가입 방지
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

    /* Id를 통해 한 명의 회원 검색, Optional<>을 return type으로 해서, null-safe한 코드 작성 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

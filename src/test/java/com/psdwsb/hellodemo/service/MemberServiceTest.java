package com.psdwsb.hellodemo.service;

import com.psdwsb.hellodemo.domain.Member;
import com.psdwsb.hellodemo.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    // memberService 내부의 memberRepository와 다른 객체인데, 왜 clearStore로 중복 문제 해결이 되는걸까?

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() {
        // * Test 코드는 한글로 적어도 무방함 (영어권에서 업무하는 게 아닌 이상)

        // given (이러한 상황이 주어졌을 때)
        Member member = new Member();
        member.setName("summer");

        // when (이걸 실행했을 때)
        Long saveId = memberService.join(member);   // join() 의 반환 값: 생성된 회원의 Id

        // then (결과가 이런게 나와야 한다)
        Member memberFound = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(memberFound.getName());
        // 참고: 자동으로 import하는 건 junit, 지금 필요한 건 assertj의 Assertions
    }

    @Test
    public void 중복회원_예외() {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("summer");
        member2.setName("summer");

        // when
        memberService.join(member1);
        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException ise) {
            assertThat(ise.getMessage()).isEqualTo("이미 존재하는 이름의 회원입니다.");
        }*/
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 이름의 회원입니다.");
    }

    /*@Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }*/
}
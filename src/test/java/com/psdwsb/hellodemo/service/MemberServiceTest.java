package com.psdwsb.hellodemo.service;

import com.psdwsb.hellodemo.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    public void 회원가입() {
        // * Test 코드는 한글로 적어도 무방함 (영어권에서 업무하는 게 아닌 이상)

        // given
        Member member = new Member();
        member.setName("hellospring");

        // when
        Long saveId = memberService.join(member);   // join() 의 반환 값: 생성된 회원의 Id

        // then
        Member foundMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @Test
    public void 중복회원_예외() {
        // given


        // when

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
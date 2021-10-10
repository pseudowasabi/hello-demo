package com.psdwsb.hellodemo.service;

import com.psdwsb.hellodemo.domain.Member;
import com.psdwsb.hellodemo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntTest {
    // MemberService 통합 테스트
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    /*@Commit*/
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setName("autumn");
        // ** @Transactional annotation을 쓰면, 여기서 insert 한 내용이 자동으로 지워짐 (rollback)
        // 안 쓰면, 그대로 insert가 되어버림

        // when
        Long saveId = memberService.join(member);

        // then
        Member memberFound = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(memberFound.getName());
    }

    @Test
    public void 중복회원_예외() {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("autumn");
        member2.setName("autumn");

        // when
        memberService.join(member1);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 이름의 회원입니다.");
    }
}

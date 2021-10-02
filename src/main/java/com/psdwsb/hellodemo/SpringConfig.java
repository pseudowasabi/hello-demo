package com.psdwsb.hellodemo;

import com.psdwsb.hellodemo.repository.MemberRepository;
import com.psdwsb.hellodemo.repository.MemoryMemberRepository;
import com.psdwsb.hellodemo.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}

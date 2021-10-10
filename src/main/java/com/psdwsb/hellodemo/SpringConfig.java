package com.psdwsb.hellodemo;

import com.psdwsb.hellodemo.repository.*;
import com.psdwsb.hellodemo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /*private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        // 설정 파일(application.properties)보고, 스프링이 자체적으로 빈 생성 - dataSource 자동 생성 및 주입
    }*/

    EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        /*return new MemoryMemberRepository();*/
        /*return new JdbcMemberRepository(dataSource);*/
        /*return new JdbcTemplateMemberRepository(dataSource);*/
        return new JpaMemberRepository(em);

        // ** 기존의 코드 그대로 두면서, 편리하게 Repository 바꿔 끼우기 가능 (interface의 다형성 특징을 활용) : Open-Closed Principle (OCP)
    }
}

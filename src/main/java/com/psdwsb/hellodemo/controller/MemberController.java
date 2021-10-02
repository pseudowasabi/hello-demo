package com.psdwsb.hellodemo.controller;

import com.psdwsb.hellodemo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // ** 3 different types of DI (Dependency injection)

    // 1. Field injection
    /*@Autowired private MemberService memberService;*/

    // 2. Setter injection
    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    // 3. Constructor injection - 가장 권장하는 방법
    /*@Autowired
    public MemberController(MemberService memberService) {
        *//* @AutoWired: Spring container에 있는 MemberService와 연결시켜 줌 *//*
        this.memberService = memberService;
    }*/
}

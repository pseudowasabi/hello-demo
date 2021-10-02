package com.psdwsb.hellodemo.controller;

import com.psdwsb.hellodemo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    @Autowired private MemberService memberService;

    /*@Autowired
    public MemberController(MemberService memberService) {
        *//* @AutoWired: Spring container에 있는 MemberService와 연결시켜 줌 *//*
        this.memberService = memberService;
    }*/
}

package com.psdwsb.hellodemo.controller;

import com.psdwsb.hellodemo.domain.Member;
import com.psdwsb.hellodemo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // ** 3 different types of DI (Dependency injection)

    // 1. Field injection
    /*@Autowired private MemberService memberService;*/

    private MemberService memberService;
    // 2. Setter injection
    /*@Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }*/

    // 3. Constructor injection - 가장 권장하는 방법
    @Autowired
    public MemberController(MemberService memberService) {
        /* @AutoWired: Spring container에 있는 MemberService와 연결시켜 줌 */
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
        // templates에서 찾음 (html 형태)
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        /*System.out.println(member.getName());*/

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}

package com.psdwsb.hellodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloDemoController {

    @GetMapping("hello")
    public String hello(Model model) {
        // static한 경우
        model.addAttribute("data", "hello~~ :)");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam(name = "name", required = false) String name, Model model) {
        // @RequestParam 있는 경우는 url에서 ? 뒤에 값을 정해주기.
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        // @ResponseBody 쓰면 viewResolver 작동 X
        // HttpMessageConverter로 StringHttpMessageConverter
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloAPI(@RequestParam("name") String name) {
        // HttpMessageConverter로 MappingJackson2HttpMessageConverter
        // 객체를 자동으로 json 타입으로 변환
        // 보통 Jackson, gson 둘 중 하나의 라이브러리를 이용
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

    }
}

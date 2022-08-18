package com.xxxx.springsecurityoauth2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        System.out.println("LoginController====================>login");
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/mian")
    public String main() {
        System.out.println("LoginController====================>login");
        return "redirect：main.html";
    }

}

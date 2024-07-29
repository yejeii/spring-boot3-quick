package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @GetMapping("/hello")
    public String niceToMeetYou(Model model) {

        model.addAttribute("username", "예지");
        return "greeting";
    }

}

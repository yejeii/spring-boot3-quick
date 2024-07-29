package com.example.demo.controller;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class ArticleController {

    // 스프링 부트가 미리 생성해 놓은 리파지토리 객체 주입(DI)
    // 해당 어노테이션 미작성시, repository NullPointerException 발생!
    // 다시 말해, @Autowired 를 통해 스프링이 알아서 객체를 생성해서 주입하고 있음을 의미함
    @Autowired
    private ArticleRepository repository;

    @GetMapping("/hello")
    public String niceToMeetYou(Model model) {

        model.addAttribute("username", "예지");
        return "greeting";
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String create(ArticleForm form) {

        // 1. 데이터 받기
        System.out.println(form.toString());
        Article article = form.toEntity();

        // 2. DB 처리
        Article saved = repository.save(article);
        System.out.println(saved.toString());

        // 3. 반환 (상세 페이지)
        return "";
    }

}

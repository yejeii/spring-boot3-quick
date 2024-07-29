package com.example.demo.controller;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Slf4j  // 로깅을 위한 어노테이션 추가
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
        log.info(form.toString());
        Article article = form.toEntity();

        // 2. DB 처리
        Article saved = repository.save(article);
        log.info(saved.toString());

        // 3. 반환 (리다이렉트: 상세 페이지)
        // Redirect: 클라가 보낸 요청을 마친 후 계속해서 처리할 다음 요청 주소 재지시하는 것
        //           분리된 기능을 하나의 연속적인 흐름으로 연결 가능
        //           리다이렉트를 받은 클라는 해당 주소로 다시 요청을 보내고, 서버는 이에 대한 결과를 응답
        return "redirect:/articles/" + saved.getId();
    }

    // 상세 조회 페이지 요청
    @GetMapping("/articles/{id}") // {id} : 변수
    public String showArticle(@PathVariable Long id, Model model) {

        // 1. Article 의 식별자 값 가져오기
        log.info("this id : " + id);

        // 2. DB 에서 조회
//        Optional<Article> article = repository.findById(id);
        Article article = repository.findById(id).orElse(null);

        // 데이터 담는 그릇
        model.addAttribute("article", article);

        // 3. 반환
        return "/articles/show";
    }

    // 목록 조회 페이지 요청
    @GetMapping("/articles")
    public String index(Model model) {

        // 1. DB 에서 조회
        ArrayList<Article> articleList = repository.findAll();

        // 2. 반환
        model.addAttribute("articleList", articleList);
        return "/articles/index";
    }

    // 게시글 수정 페이지 요청
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        log.info("this id : " + id);

        // 1. DB 에서 해당 게시글 조회
        Article article = repository.findById(id).orElse(null);

        // 2. 반환
        model.addAttribute("article", article);
        return "/articles/edit";
    }

    // 게시글 수정 처리
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {

        // 1. DTO 확인
        log.info(form.toString());

        // 2. 엔티티 변경, DB 에서 수정
        Article article = form.toEntity();
        log.info(article.toString());

        // 2-1. 기존 데이터와의 비교 작업
        Article target = repository.findById(article.getId()).orElse(null);

        // 2-2. 기존 데이터가 null 이 아닐 경우에만 갱신
        if(target != null) {
            repository.save(article);
        }

        // 4. 반환 (redirect: 상세 페이지)
        return "redirect:/articles/" + article.getId();
    }


}

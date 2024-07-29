package com.example.demo.api;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Talend API Tester 에서 테스트
@Slf4j
@RestController
public class ArticleApiController {

    // DI
    @Autowired
    private ArticleService service;

    // 1. GET : 전체 게시물 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> index() {
        List<Article> articles = service.index();
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    // 2. GET : 단일 게시물 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id) {
        Article article = service.show(id);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    // 3. POST : 단일 게시물 생성
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = service.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 4. PATCH : 단일 게시물 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        log.info("update target : id - {}, dto - {}", id, dto.toString());

        Article updated = service.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 5. DELETE : 단일 게시물 수정
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        Article deleted = service.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


    }

}

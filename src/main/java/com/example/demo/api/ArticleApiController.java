package com.example.demo.api;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
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
    private ArticleRepository repository;

    // 1. GET : 전체 게시물 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> index() {
        List<Article> articles = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    // 2. GET : 단일 게시물 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id) {
        Article article = repository.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    // 3. POST : 단일 게시물 생성
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm form) {
        Article article = form.toEntity();
        repository.save(article);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    // 4. PATCH : 단일 게시물 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        log.info("update target : id - {}, dto - {}", id, dto.toString());

        // 1. 엔티티로 변환
        Article article = dto.toEntity();

        // 2. DB 에서 조회
        Article target = repository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리 ( 400 )
        if(target == null || id != article.getId())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        // 4. 업데이트 처리 및 정상 응답
        target.patch(article);  // 기존 데이터(target)에 새 데이터(article) 붙이기
        Article updated = repository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 5. DELETE : 단일 게시물 수정
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        // 1. 대상 찾기
        Article target = repository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if(target == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        // 3. 대상 삭제
        repository.delete(target);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

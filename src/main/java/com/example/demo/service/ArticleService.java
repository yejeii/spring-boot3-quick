package com.example.demo.service;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository arepository;

    // 1. GET 방식의 모든 게시물 조회 처리
    public List<Article> index() {
        return arepository.findAll();
    }

    // 2. GET 방식의 단일 게시물 조회 처리
    public Article show(Long id) {
        return arepository.findById(id).orElse(null);
    }

    // 3. POST 방식의 단일 게시물 등록 처리
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
            return null;
        return arepository.save(article);
    }

    // 4. PATCH 방식의 단일 게시물 수정 처리
    public Article update(Long id, ArticleForm dto) {

        // 1. 엔티티로 변환
        Article article = dto.toEntity();

        // 2. DB 에서 조회
        Article target = arepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리 ( 400 )
        if(target == null || id != article.getId()) {
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 4. 업데이트 처리
        target.patch(article);  // 기존 데이터(target)에 새 데이터(article) 붙이기
        return arepository.save(target);
    }

    // 4. DELETE 방식의 단일 게시물 삭제 처리
    public Article delete(Long id) {

        // 1. 대상 찾기
        Article target = arepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if(target == null)
            return null;

        // 3. 대상 삭제
        arepository.delete(target);
        return target;
    }
}

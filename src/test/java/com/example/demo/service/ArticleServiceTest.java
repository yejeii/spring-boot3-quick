package com.example.demo.service;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 스프링 부트와 해당 클래스를 연동해 테스트할 때 사용하는 어노테이션
// 테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있음
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void index() {

        // given: 예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(4L, "2222", "2222");
        Article c = new Article(5L, "3333", "3333");
        Article d = new Article(6L, "4444", "4444");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c, d));

        // when: 실제 데이터 획득
        List<Article> articles = articleService.index();

        // 비교. 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {

        // given: 예상 데이터
        Long id = 4L;
        Article expected = new Article(id, "2222", "2222");

        // when: 실제 데이터 획득
        Article actual = articleService.show(id);

        // 비교. 검증
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {

        // given: 예상 데이터
        Long id = -1L;
        Article expected = null;

        // when: 실제 데이터 획득
        Article actual = articleService.show(id);   // null

        // 비교. 검증
        // 둘 다 null 이면 동일한 것으로 간주
        assertEquals(expected, actual);
    }

    @Transactional  // 테스트 성공여부와 관계없이 테스트 종료 후 원래대로 롤백
    @Test
    void create_성공_title과_content만_있는_dto_입력() {

        // given: 예상 데이터
        String title = "1010";
        String content = "1010";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(12L, title, content);    // IDENTITY : DB 에서 Auto_Increment -> 해당 값에 맞춰 id 값 설정안하면 계속 실패함!

        // when: 실제 데이터
        Article actual = articleService.create(dto);

        // 비교. 검증
        assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @Test
    void create_실패_id가_포함된_dto_입력() {

        // given: 예상 데이터
        Long id = 11L;
        String title = "5555";
        String content = "5555";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // when: 실제 데이터
        Article actual = articleService.create(dto);    // null

        // 비교. 검증
        assertEquals(expected, actual);
    }

    @Transactional
    @Test
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {

        // given: 예상 데이터
        Long id = 4L;
        String title = "2222";
        String content = "2424";    // 수정
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = dto.toEntity();

        // when: 실제 데이터
        Article actual  = articleService.update(id, dto);

        // 비교. 검증
        assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @Test
    void update_성공_존재하는_id와_title만_있는_dto_입력() {

        // given: 예상 데이터
        Long id = 4L;
        String title = "4444";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = dto.toEntity();
        expected.setContent("2222");

        // when: 실제 데이터
        Article actual  = articleService.update(id, dto);

        // 비교. 검증
        assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @Test
    void update_실패_존재하지_않는_id의_dto_입력() {

        // given: 예상 데이터
        Long id = -1L;
        String title = "2222";
        String content = "2222";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;    // Service.update() 에서 못찾으면 null 을 반환하므로.

        // when: 실제 데이터
        Article actual = articleService.update(id, dto);

        // 비교. 검증
        assertEquals(expected, actual);
    }

    @Transactional
    @Test
    void delete_성공_존재하는_id_입력() {

        // given: 예상 데이터
        Long id = 4L;
        String title = "2222";
        String content = "2222";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = dto.toEntity();

        // when: 실제 데이터
        Article actual  = articleService.delete(id);

        // 비교. 검증
        assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @Test
    void delete_실패_존재하지_않는_id_입력() {

        // given: 예상 데이터
        Long id = -1L;
        Article expected = null;    // Service.delete() 에서 못찾으면 null 을 반환하므로.

        // when: 실제 데이터
        Article actual  = articleService.delete(id);

        // 비교. 검증
        assertEquals(expected, actual);
    }
}
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    // 일부 데이터만 수정하는 경우, 다른 데이터는 null 이 되는 PATCH 문제 해결
    public void patch(Article article) {
        if(article.title != null) // 수정 데이터가 있는 경우
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}

package com.example.demo.dto;

import com.example.demo.entity.Article;
import lombok.*;

@Data
@AllArgsConstructor
public class ArticleForm {
    private Long id;
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, this.title, this.content);
    }
}

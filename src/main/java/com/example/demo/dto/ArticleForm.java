package com.example.demo.dto;

import com.example.demo.entity.Article;
import lombok.*;

@Data
@AllArgsConstructor
public class ArticleForm {
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(null, this.title, this.content);
    }
}

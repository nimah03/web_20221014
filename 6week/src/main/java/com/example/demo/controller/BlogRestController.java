package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.demo.model.domain.Article;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BlogRestController {

    @Autowired
    private BlogService blogService;

    // 💡 메서드 선언: List<Article>를 반환하겠다고 선언
    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> listArticles() {

        // 1. Service를 통해 게시글 '목록' 전체를 조회합니다.
        List<Article> articles = blogService.findAll();

        // 2. HTTP 상태 코드 200(OK)과 함께 '목록'을 반환합니다.
        // 수정 전: return ResponseEntity.ok(article); (단일 객체)
        // 수정 후:
        return ResponseEntity.ok(articles); // 💡 List<Article> 객체를 반환해야 합니다.
    }
}

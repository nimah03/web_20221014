package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.domain.Board; // 🚨 Article 대신 Board 임포트 🚨
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

@RequiredArgsConstructor
@RestController
public class BlogRestController {

    private final BlogService blogService;

    // 🚨 반환 타입을 Board로 수정 🚨
    @PostMapping("/api/articles") 
    public ResponseEntity<Board> addArticle(@ModelAttribute AddArticleRequest request) {
        // 🚨 Board 타입으로 받도록 수정 🚨
        Board saved = blogService.save(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
        // favicon 오류 방지
    }
}

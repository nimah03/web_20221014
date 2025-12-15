package com.example.demo.model.service; // 이 파일의 위치는 DTO로 사용되므로 service 패키지가 아닐 수 있습니다.

import com.example.demo.model.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DTO는 @Entity를 사용하지 않고 @Getter, @NoArgsConstructor, @AllArgsConstructor만 사용합니다.
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 포함하는 생성자
@Getter // Getter 메서드
public class AddArticleRequest {

    // 클라이언트로부터 받을 데이터
    private String title;
    private String content;

    // 이 DTO 객체를 Article 엔티티로 변환하는 메서드
    public Article toEntity() {
        // Article.java에 @Builder가 있어야만 작동합니다.
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
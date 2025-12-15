package com.example.demo.model.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id; // 이제 사용됩니다.
import javax.persistence.GeneratedValue; // 이제 사용됩니다.
import javax.persistence.GenerationType; // 이제 사용됩니다.
import javax.persistence.Column; // 이제 사용됩니다.

@Entity
@Table(name = "article")
public class Article {

    // 💡 ID 필드와 관련 어노테이션 추가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 💡 제목 (title) 필드와 @Column 어노테이션 추가
    @Column(nullable = false, length = 100) // null을 허용하지 않고 길이를 100으로 제한
    private String title;

    // 💡 내용 (content) 필드와 @Column 어노테이션 추가
    @Column(nullable = false, columnDefinition = "TEXT") // null을 허용하지 않고 타입을 TEXT로 지정
    private String content;

    // JPA 필수: 기본 생성자
    public Article() {
    }

    // ... (Getter, Setter, toString 등 나머지 메서드) ...
}
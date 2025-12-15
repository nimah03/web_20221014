package com.example.demo.model.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id; // 이제 사용됩니다.
import javax.persistence.GeneratedValue; // 이제 사용됩니다.
import javax.persistence.GenerationType; // 이제 사용됩니다.
import javax.persistence.Column; // 이제 사용됩니다.
import lombok.Builder; // 💡 builder() 메서드를 자동 생성합니다!
import lombok.Getter;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "article")
@Getter
@AllArgsConstructor
@Builder // 이 어노테이션이 핵심입니다.
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

    public void update(String title, String content) { // 현재 객체 상태 업데이트
        this.title = title;
        this.content = content;
    }

    // ... (Getter, Setter, toString 등 나머지 메서드) ...
}
package com.example.demo.model.domain;

import javax.persistence.*; // Spring Boot 2.x에서는 javax를 사용합니다.
import lombok.*;          // Lombok 사용

@Entity
@Getter             // 모든 필드에 Getter 생성
@Setter             // 🚨 모든 필드에 Setter 생성 (오류 해결 핵심) 🚨
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA를 위한 기본 생성자
public class Board {

    // 1. Primary Key 필드 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    // 2. 게시글 핵심 필드 (제목, 내용)
    @Column(name = "title", nullable = false) 
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // 3. 7주차에 추가된 필드: user, newdate, count, likec
    @Column(name = "user", nullable = false) // 이름 (작성자)
    private String user = "";
    
    @Column(name = "newdate", nullable = false) // 날짜
    private String newdate = ""; 

    @Column(name = "count", nullable = false) // 조회수
    private String count = ""; 

    @Column(name = "likec", nullable = false) // 좋아요
    private String likec = "";

    // Lombok을 사용하지 않는 경우, 이 필드들에 대한 public void setTitle(String title) {} 형태의 메서드를 직접 추가해야 합니다.
    // Board.java 내부에 있어야 함
    @Builder
    public Board(String title, String content, String user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}

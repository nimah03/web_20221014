// src/main/java/com/example/demo/model/domain/Member.java
package com.example.demo.model.domain;

import javax.persistence.*;
import lombok.*; // 🚨 [필수] Lombok의 모든 어노테이션을 사용하기 위해 import

@Getter // 🚨 [추가] getId(), getPassword() 등 Getter 메서드 생성을 위해 필수! (getId(), getPassword() 오류 해결)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA를 위한 기본 생성자
@Entity // JPA 엔티티 클래스
@Table(name = "Member") // 테이블명 지정
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    @Column(name = "id", updatable = false) // 수정 x
    private Long id; // 🚨 getId() 메서드는 @Getter가 처리합니다.
    
    @Column(name = "name", nullable = false) 
    private String name = "";
    
    @Column(name = "email", unique = true, nullable = false) 
    private String email = "";
    
    @Column(name = "password", nullable = false) 
    private String password = ""; // 🚨 getPassword() 메서드는 @Getter가 처리합니다.
    
    @Column(name = "age", nullable = false)
    private String age = "";
    
    @Column(name = "mobile", nullable = false)
    private String mobile = "";
    
    @Column(name = "address", nullable = false)
    private String address = "";
    
    @Builder // 생성자에 빌더 패턴 적용
    public Member(String name, String email, String password, String age, String mobile, String address){
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}
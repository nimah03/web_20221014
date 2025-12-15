package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.domain.TestDB;

// JpaRepository<엔티티 클래스, 기본키 타입>
public interface TestRepository extends JpaRepository<TestDB, Long> {

    // 1. 이름으로 조회하는 메서드 (기존)
    TestDB findByName(String name);

    // 2. ID로 조회하는 메서드를 추가합니다.
    TestDB findById(long id); // Long 대신 primitive long 사용 가능

    // (선택) ID로 조회하는 다른 방법 (기본 JpaRepository가 제공하는 Optional<T> findById(ID id)를 사용해도
    // 됨)
    // Optional<TestDB> findById(Long id);
}
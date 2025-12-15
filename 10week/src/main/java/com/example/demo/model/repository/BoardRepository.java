package com.example.demo.model.repository;

import com.example.demo.model.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 7주차에서는 추가 메소드 없음
    // 기본 CRUD 기능만 사용

}
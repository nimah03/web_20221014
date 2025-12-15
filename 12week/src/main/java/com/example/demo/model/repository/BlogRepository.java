package com.example.demo.model.repository;

import com.example.demo.model.domain.Board;
import org.springframework.data.domain.Page;
// 🚨 누락된 import 구문 추가 (가장 중요) 🚨
import org.springframework.data.domain.Pageable; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Board, Long> {
    
    // 이 메서드에서 Pageable을 사용하고 있습니다.
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
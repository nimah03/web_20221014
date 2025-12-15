package com.example.demo.model.repository;

import com.example.demo.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // 🚨 Pageable 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    // 🚨 검색 쿼리 메서드 추가: 제목(Title)에 키워드가 포함된 결과를 페이징하여 반환 🚨
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
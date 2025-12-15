// src/main/java/com/example/demo/model/service/BlogService.java
package com.example.demo.model.service;

import com.example.demo.model.domain.Board; 
import com.example.demo.model.repository.BoardRepository; 
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional; // 🚨 [필수] Optional 타입 사용을 위해 import 합니다.

// import com.example.demo.model.service.AddArticleRequest; // DTO 파일도 필요합니다.

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {
    
    private final BoardRepository boardRepository; 
    
    // --------------------------------------------------------------------------
    // [1. 게시글 전체 조회]
    // --------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<Board> findAll(Pageable pageable) { 
        // 실제 로직: return boardRepository.findAll(pageable);
        return Page.empty(); 
    }

    // --------------------------------------------------------------------------
    // [2. 키워드 검색 및 페이징 조회]
    // --------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        // 실제 로직: return boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        return Page.empty(); 
    }

    // --------------------------------------------------------------------------
    // [3. 단일 게시글 조회] 🚨 [수정] 반환 타입을 Optional<Board>로 변경하여 BlogController 오류 해결
    // --------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public Optional<Board> findById(Long id) {
        // 실제 로직: return boardRepository.findById(id); 
        return Optional.empty(); // 컴파일 오류 해결을 위해 임시로 Optional.empty() 반환
    }

    // --------------------------------------------------------------------------
    // [4. 게시글 수정] 🚨 [수정] 임시 반환값을 null로 변경 (생성자 접근 오류 해결)
    // --------------------------------------------------------------------------
    @Transactional
    public Board update(Long id, AddArticleRequest request) {
        // 실제 로직 구현 필요
        return null; // 임시 반환값
    }

    // --------------------------------------------------------------------------
    // [5. 게시글 삭제]
    // --------------------------------------------------------------------------
    @Transactional
    public void delete(Long id) {
        // boardRepository.deleteById(id);
    }
    
    // --------------------------------------------------------------------------
    // [6. 게시글 저장] 🚨 [수정] 임시 반환값을 null로 변경 (생성자 접근 오류 해결)
    // --------------------------------------------------------------------------
    public Board save(AddArticleRequest request) {
        // 실제 로직: return boardRepository.save(request.toEntity()); 
        return null; // 임시 반환값
    }
}
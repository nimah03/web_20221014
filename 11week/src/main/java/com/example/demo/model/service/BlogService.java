package com.example.demo.model.service;

import com.example.demo.model.domain.Board;
// 🚨 DTO 클래스 import 확인 🚨
import com.example.demo.model.service.AddArticleRequest; 
import com.example.demo.model.repository.BoardRepository; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 🚨 11주차 페이징/검색을 위한 import 추가 🚨
import org.springframework.data.domain.Page;          
import org.springframework.data.domain.PageRequest;  
import org.springframework.data.domain.Pageable;     


@Service
public class BlogService {

    private final BoardRepository boardRepository;

    // 생성자 주입
    public BlogService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // ----------------------------------------------------------------------
    // [1. 게시글 목록 조회 - 페이징 기능 추가] (기존 List<Board> findAll() 수정)
    // ----------------------------------------------------------------------
    /**
     * 전체 게시글을 페이징하여 조회합니다. (Controller 오류 1 해결)
     * 기존 List<Board> findAll()은 주석 처리하거나 삭제해야 합니다.
     */
    public Page<Board> findAll(Pageable pageable) { 
        // JpaRepository의 findAll(Pageable pageable)을 사용합니다.
        return boardRepository.findAll(pageable); 
    }
    
    // ----------------------------------------------------------------------
    // [2. 특정 키워드로 검색 - 검색 기능 추가] (Controller 오류 2 해결)
    // ----------------------------------------------------------------------
    /**
     * 특정 키워드를 포함하는 게시글을 페이징하여 조회합니다.
     */
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) { 
        // BoardRepository에 정의된 findByTitleContaining 쿼리 메서드를 사용합니다.
        return boardRepository.findByTitleContaining(keyword, pageable); 
    }
    
    // ----------------------------------------------------------------------
    // [기존 코드: 단건 조회]
    // ----------------------------------------------------------------------
    public Optional<Board> findById(Long id) { 
        return boardRepository.findById(id);
    }
    
    // ----------------------------------------------------------------------
    // [7주차 핵심 기능: 저장, 수정, 삭제]
    // ----------------------------------------------------------------------

    /**
     * 게시글 저장 로직 
     */
    @Transactional
    public Board save(AddArticleRequest request){
        // 7주차에 설정된 newdate, count, likec 초기화 로직은 DTO의 toEntity() 또는 
        // Service에서 직접 처리해야 합니다. 현재 DTO의 toEntity()가 모두 처리한다고 가정합니다.
        
        // 🚨 참고: 7주차 정식 코드에서는 보통 Service에서 초기값을 설정합니다.
        Board board = request.toEntity();
        
        // 7주차 초기값 설정 (누락되었던 부분을 다시 추가하는 것이 권장됨)
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        board.setNewdate(now); 
        board.setCount("0");
        board.setLikec("0");
        
        return boardRepository.save(board);
    }

    /**
     * 게시글 수정 로직 
     */
    @Transactional
    public Board update(Long id, AddArticleRequest request) { 
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패: ID " + id + "가 존재하지 않습니다."));

        board.setTitle(request.getTitle()); 
        board.setContent(request.getContent()); 
        
        return board;
    }

    /**
     * 게시글 삭제 로직 
     */
    public void delete(Long id) { 
        boardRepository.deleteById(id);
    }
}
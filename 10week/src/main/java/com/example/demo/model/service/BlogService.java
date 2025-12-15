package com.example.demo.model.service;

import com.example.demo.model.domain.Board;
// 🚨 DTO 클래스 import 추가 🚨
import com.example.demo.model.service.AddArticleRequest; 
import com.example.demo.model.repository.BoardRepository; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.Optional;
// 🚨 날짜/시간 처리를 위한 import 추가 🚨
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BlogService {

    private final BoardRepository boardRepository;

    // 생성자 주입
    public BlogService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // ----------------------------------------------------------------------
    // [기존 코드: 조회]
    // ----------------------------------------------------------------------
    public List<Board> findAll() { 
        return boardRepository.findAll();
    }

    public Optional<Board> findById(Long id) { 
        return boardRepository.findById(id);
    }
    
    // ----------------------------------------------------------------------
    // [7주차 오류 해결을 위해 추가하는 코드]
    // ----------------------------------------------------------------------

    /**
     * 게시글 저장 로직 (BlogRestController의 save 메서드 오류 해결)
     */
    @Transactional
    public Board save(AddArticleRequest request) { // 🚨 save 메서드 구현 🚨
        
        // 1. DTO의 toEntity() 메서드를 통해 Board 객체 생성
        // DTO에 toEntity() 메서드가 있어야 작동합니다.
        Board board = request.toEntity(); 
        
        // 2. 7주차에 추가된 필드의 초기값 설정
        // 현재 날짜/시간 설정
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        board.setNewdate(now); 
        
        // 조회수 및 좋아요 초기값 0 설정
        board.setCount("0");
        board.setLikec("0");
        
        // 3. 리포지토리를 통해 DB에 저장
        return boardRepository.save(board);
    }

    /**
     * 게시글 수정 로직 (BlogController의 updateBoard 메서드 오류 해결)
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
     * 게시글 삭제 로직 (BlogController의 deleteBoard 메서드 오류 해결)
     */
    public void delete(Long id) { 
        boardRepository.deleteById(id);
    }
}
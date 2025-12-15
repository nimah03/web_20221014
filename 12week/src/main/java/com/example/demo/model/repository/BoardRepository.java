package com.example.demo.model.repository;

import com.example.demo.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Board 엔티티와 Long 타입의 ID를 사용하는 JpaRepository를 상속합니다.
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 🚨 [오류 수정] 이 메서드 정의가 없어서 컴파일 에러가 발생했습니다. 🚨
    // Spring Data JPA의 쿼리 메서드 규칙에 따라 findBy[필드 이름]Containing 메서드를 추가합니다.
    // 제목(title) 필드에서 키워드를 포함하는(Containing) 게시글을 검색합니다.
    // Pageable 객체를 사용하여 페이징된 결과를 반환합니다.
    Page<Board> findByTitleContaining(String title, Pageable pageable);
}
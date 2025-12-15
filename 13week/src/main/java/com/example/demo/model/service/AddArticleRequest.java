package com.example.demo.model.service; 

import com.example.demo.model.domain.Board; // Board 엔티티 임포트 (경로 확인)
import lombok.Builder; // 🚨 toEntity() 사용 시 필요 🚨
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter 
public class AddArticleRequest {

    private String title;
    private String content;
    private String user; // 7주차에 추가된 작성자 필드

    // --------------------------------------------------------
    // 🚨 오류 해결을 위한 필수 메서드: toEntity() 🚨
    // --------------------------------------------------------
    
    /**
     * DTO 객체를 Board 엔티티로 변환하는 메서드입니다.
     */
    public Board toEntity() {
        // Board 엔티티에 @Builder가 있다면 이를 사용합니다.
        return Board.builder()
                .title(title)
                .content(content)
                .user(user) 
                // newdate, count, likec 필드는 Service 계층에서 초기화됩니다.
                .build();
    }
}
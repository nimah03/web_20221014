package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping; // 글쓰기 저장을 위해 POST Mapping 추가 (추정)

import java.util.Optional;
import com.example.demo.model.domain.Board;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.AddArticleRequest; // DTO는 Board용으로 변경해야 하지만, 기존 구조 유지를 위해 임시 사용
import com.example.demo.model.service.BlogService;
import com.example.demo.model.service.TestService;


@Controller
public class BlogController {

    @Autowired
    TestService testService; 

    @Autowired
    private BlogService blogService;

    // [이전 기능] 메인 페이지, About 등
    
    @GetMapping("/hello") 
    public String hello(Model model) {
        model.addAttribute("data", " 방갑습니다.");
        return "hello"; 
    }

    @GetMapping("/about_detailed")
    public String about_detailed() {
        return "about_detailed";
    }
    

    // [TestDB 기능]
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test1 = testService.findByName("홍길동"); 
        TestDB test2 = testService.findById(2);
        TestDB test3 = testService.findById(3);

        model.addAttribute("data1", test1);
        model.addAttribute("data2", test2);
        model.addAttribute("data3", test3);

        System.out.println("데이터1 출력 디버그 : " + test1);
        System.out.println("데이터2 출력 디버그 : " + test2);
        System.out.println("데이터3 출력 디버그 : " + test3);

        return "testdb";
    }

    // =========================================================
    // [7주차 핵심 변경 사항: Board로 전환 및 기능 추가/수정]
    // =========================================================

    // 1. 게시글 목록 조회 (Article -> Board로 변경됨)
    // 7주차 자료에는 명시되지 않았으나, 목록이 없으면 게시판이 동작하지 않으므로 추가합니다.
    @GetMapping("/board_list") 
    public String board_list(Model model) {
        List<Board> list = blogService.findAll(); // Board 엔티티 리스트 조회
        model.addAttribute("boards", list); // Model에 'boards'라는 이름으로 추가
        return "board_list"; // board_list.html 연결
    }

    // 2. 글쓰기 페이지 이동 (7주차 자료에 명시된 코드)
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    // 3. 특정 게시글 내용 보기 (7주차 자료에 명시된 코드)
    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> boardOptional = blogService.findById(id); // Optional<Board>로 변경
        
        if (boardOptional.isPresent()) {
            model.addAttribute("boards", boardOptional.get()); // boards 객체에 담아 전달
        } else {
            return "/error_page/article_error"; 
        }
        return "board_view";
    }

    // 4. 글 수정 페이지 이동 (7주차 자료의 board_list.html에서 요청하는 맵핑)
    // 7주차 자료에 코드가 없으므로, article_edit 로직을 기반으로 추정하여 추가합니다.
    @GetMapping("/board_edit/{id}") 
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> boardOptional = blogService.findById(id); 
        
        if (boardOptional.isPresent()) {
            model.addAttribute("boards", boardOptional.get()); // boards 객체에 담아 전달
        } else {
            return "/error_page/article_error";
        }
        return "board_edit"; // board_edit.html 연결
    }

    // 5. 게시글 수정 API (Article -> Board로 변경)
    // 7주차 자료에 명시된 PUT 요청을 Board 엔티티에 맞게 추정하여 수정합니다.
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        // DTO 이름은 AddArticleRequest 그대로 사용한다고 가정합니다.
        blogService.update(id, request); 
        return "redirect:/board_list";
    }

    // 6. 게시글 삭제 API (Article -> Board로 변경, 7주차 자료의 board_list.html form과 일치)
    // board_list.html의 form은 POST로 DELETE를 흉내냅니다
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }
}
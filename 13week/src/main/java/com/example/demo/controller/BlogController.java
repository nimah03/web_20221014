package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping; 

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.demo.model.domain.Board;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.AddArticleRequest; 
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
 // [1. 게시글 목록 조회 - 글 번호 순번 계산 로직 추가]
 // =========================================================

    @GetMapping("/board_list") 
    public String board_list(
        Model model, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "") String keyword, HttpSession session){ // 세션 객체 전달
            String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
            String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인
            if (userId == null) {
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
            }
            System.out.println("세션 userId: " + userId); // 서버 IDE 터미널에 세션 값 출력) {
            int pageSize = 3; // 한 페이지당 게시글 수
            PageRequest pageable = PageRequest.of(page, pageSize); 
            Page<Board> list; 
    
        if (keyword.isEmpty()) {
        list = blogService.findAll(pageable); 
         } else {
             list = blogService.searchByKeyword(keyword, pageable); 
        }
        
        // 🚨 글 번호 시작점 계산 (순번 출력용) 🚨
        int startNum = (page * pageSize) + 1; 
        
         // 뷰(HTML)로 필요한 데이터 전달
        model.addAttribute("totalPages", list.getTotalPages()); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("keyword", keyword); 
        
        // 🚨 HTML로 시작 번호 전달 🚨
        model.addAttribute("startNum", startNum);
        model.addAttribute("email", email); // 로그인 사용자(이메일)

     return "board_list"; 
    }
//     
//     // -------------------------------------------------------------------------
//     // [2. 글쓰기 페이지 이동]
//     // -------------------------------------------------------------------------
    @GetMapping("/board_write")
    public String board_write() {
    return "board_write";
    }

// // -------------------------------------------------------------------------
//     // [3. 특정 게시글 내용 보기]
//     // -------------------------------------------------------------------------
    @GetMapping("/board_view/{id}") 
    public String board_view(Model model, @PathVariable Long id) {
         Optional<Board> boardOptional = blogService.findById(id); 

    if (boardOptional.isPresent()) {
            model.addAttribute("boards", boardOptional.get()); 
        } else {
             return "/error_page/article_error"; 
        }
        return "board_view";
     }

//     // -------------------------------------------------------------------------
//     // [4. 글 수정 페이지 이동]
//     // -------------------------------------------------------------------------
    @GetMapping("/board_edit/{id}") 
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> boardOptional = blogService.findById(id); 
        
        if (boardOptional.isPresent()) {
            model.addAttribute("boards", boardOptional.get()); 
        } else {
        return "/error_page/article_error";
        }
        return "board_edit";
    }

//     // -------------------------------------------------------------------------
//     // [5. 게시글 수정 API]
//     // -------------------------------------------------------------------------
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request); 
         return "redirect:/board_list";
    }

//     -------------------------------------------------------------------------
//     // [6. 게시글 삭제 API] - @DeleteMapping을 @PostMapping으로 변경 (HTML 폼 요청에 맞춤)
//     // -------------------------------------------------------------------------
    @PostMapping("/api/board_delete/{id}") // 🚨 @DeleteMapping -> @PostMapping으로 수정
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
         return "redirect:/board_list";
}

//     // -------------------------------------------------------------------------
//     // [7. 게시글 저장 API (HTML 폼 처리)]
//     // -------------------------------------------------------------------------
   // src/main/java/com/example/demo/controller/BlogController.java 내에서


@PostMapping("/boards") // 경로를 /api/articles 에서 /boards 로 변경
public String addboards(AddArticleRequest request) { 
    blogService.save(request); 
    return "redirect:/board_list"; // 저장 후 게시글 목록으로 리다이렉트
}
}
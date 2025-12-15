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

import java.util.Optional;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import com.example.demo.model.service.Testservice; // 'Testservice' -> 'TestService'로 수정

@Controller // 컨트롤러 어노테이션 명시
public class BlogController {

    // TestService 객체 선언은 여기에 딱 한 번만 합니다.
    @Autowired
    Testservice testservice;



    @Autowired
    private BlogService blogService;

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = blogService.findAll();
        model.addAttribute("articles", list);
        return "article_list";
    }

    @GetMapping("/hello") // 전송 방식

    public String hello(Model model) {
        model.addAttribute("data", " 방갑습니다."); // model 설정
        return "hello"; // hello.html 연결
    }

    @GetMapping("/about_detailed")
    public String about_detailed() {
        return "about_detailed";
    }


    @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    public String article_edit(Model model, @PathVariable Long id) {
        Optional list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결(이름 수정됨)
        }
        return "article_edit"; // .HTML 연결
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 .html 연결
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }

    // getAllTestDBs 메서드 정의는 여기에 딱 한 번만 합니다.
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        // 1. 사용자 1 (홍길동) 조회
        TestDB test1 = testservice.findByName("홍길동");

        // 2. 사용자 2 (김철수, ID 2) 조회
        TestDB test2 = testservice.findById(2);

        // 3. 사용자 3 (이영희, ID 3) 조회
        TestDB test3 = testservice.findById(3);

        // 모델에 데이터 추가 (이전 data4 대신 data1, data2, data3 사용)
        model.addAttribute("data1", test1);
        model.addAttribute("data2", test2);
        model.addAttribute("data3", test3);

        // 디버그 출력
        System.out.println("데이터1 출력 디버그 : " + test1);
        System.out.println("데이터2 출력 디버그 : " + test2);
        System.out.println("데이터3 출력 디버그 : " + test3);

        return "testdb";
    }
}

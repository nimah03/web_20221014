package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.Testservice; // 'Testservice' -> 'TestService'로 수정

@Controller // 컨트롤러 어노테이션 명시
public class DemoController {

    // TestService 객체 선언은 여기에 딱 한 번만 합니다.
    @Autowired
    Testservice testservice; // 'Testservice' -> 'TestService'로 수정

    @GetMapping("/hello") // 전송 방식

    public String hello(Model model) {
        model.addAttribute("data", " 방갑습니다."); // model 설정
        return "hello"; // hello.html 연결
    }

    @GetMapping("/about_detailed")
    public String about_detailed() {
        return "about_detailed";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", 01);
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", 002);
        return "thymeleaf_test1";
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

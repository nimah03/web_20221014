package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // 추가

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService; // 'Testservice' -> 'TestService'로 수정

@Controller 
@RequestMapping("/demo") // ✨ DemoController만의 고유한 기본 경로 설정
public class DemoController {

    @Autowired
    TestService testService; 

    // 기존의 '/hello' 경로가 BlogController에 있었으므로 충돌을 피하기 위해
    // 이 메서드는 삭제하거나 @RequestMapping("/demo")의 영향을 받아 '/demo/hello'로 변경됩니다.
    // 여기서는 충돌을 피하기 위해 DemoController의 hello()는 삭제합니다.

    // 기존의 '/about_detailed' 경로가 BlogController에 있었으므로 충돌을 피하기 위해
    // 이 메서드는 @RequestMapping("/demo")의 영향을 받아 '/demo/about_detailed'로 변경됩니다.
    @GetMapping("/about_detailed")
    public String about_detailed() {
        return "about_detailed"; // 뷰 이름은 그대로 사용 가능
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
    
}
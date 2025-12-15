// src/main/java/com/example/demo/controller/MemberController.java
package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.Cookie; // 🚨 [필수] 쿠키 사용
import javax.servlet.http.HttpServletRequest; // 🚨 [필수] 요청 객체 사용
import javax.servlet.http.HttpServletResponse; // 🚨 [필수] 응답 객체 사용
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.service.MemberService;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.domain.Member; 

@Controller 
public class MemberController {
    
    @Autowired 
    private MemberService memberService; 
    
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new";
    }
    
    @GetMapping("/member_login")
    public String member_login() {
        return "login";
    }
    
    // 🚨 [수정된 checkMembers 메서드] 세션 무효화 및 쿠키 삭제 로직 추가
    @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    public String checkMembers(
            @ModelAttribute AddMemberRequest request, 
            Model model, 
            HttpServletRequest request2, // HttpServletRequest를 사용
            HttpServletResponse response // HttpServletResponse를 사용
    ) {
        try {
            // 1. 로그인 검증
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            model.addAttribute("member", member); 
            
            // 2. 기존 세션 정리 (요청하신 로직)
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화
                
                // JSESSIONID 쿠키 삭제
                Cookie cookie = new Cookie("JSESSIONID", null); 
                cookie.setPath("/"); 
                cookie.setMaxAge(0); 
                response.addCookie(cookie); 
            }
            
            // 3. 새 세션 생성 및 정보 설정
            session = request2.getSession(true); // 새로운 세션 생성
            String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID
            String email = request.getEmail(); 
            
            session.setAttribute("userId", sessionId); 
            session.setAttribute("email", email); 
            
            return "redirect:/board_list";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
    
    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end";
    }
}
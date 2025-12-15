// src/main/java/com/example/demo/controller/MemberController.java
package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.Cookie; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
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
    
    @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    public String checkMembers(
            @ModelAttribute AddMemberRequest request, 
            Model model, 
            HttpServletRequest request2, 
            HttpServletResponse response 
    ) {
        try {
            // 1. 로그인 검증
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            model.addAttribute("member", member); 
            
            // 2. 기존 세션 정리 (보안 강화를 위해 기존 세션 무효화 후 새로 생성)
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

    // 🚨 [추가된 로그아웃 메서드]
    @GetMapping("/api/logout") // 로그아웃 동작
    public String member_logout(
            Model model, 
            HttpServletRequest request2, 
            HttpServletResponse response // 띄어쓰기 오류 수정
    ) {
        try {
            // 1. 기존 세션 가져오기 및 무효화
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화
            }
            
            // 2. JSESSIONID 쿠키 삭제
            Cookie cookie = new Cookie("JSESSIONID", null); 
            cookie.setPath("/"); 
            cookie.setMaxAge(0); 
            response.addCookie(cookie); 
            
            // 3. 새로운 세션 생성 (필요하다면)
            session = request2.getSession(true); 
            System.out.println("새로운 세션 ID: " + session.getId()); 
            
            return "login"; // 로그인 페이지로 리다이렉트  
        } catch (Exception e) { // IllegalArgumentException 대신 일반적인 Exception으로 처리
            // 세션이 null인 상태에서 invalidate를 호출하면 NullPointerException이 발생할 수 있으므로 Exception으로 포괄 처리
            System.out.println("로그아웃 중 오류 발생: " + e.getMessage());
            return "login"; 
        }
    }
}
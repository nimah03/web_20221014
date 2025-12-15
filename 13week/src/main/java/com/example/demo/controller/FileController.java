// src/main/java/com/example/demo/controller/FileController.java
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; // 🚨 [필수] IOException 처리
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    // 🚨 [필수] @Value는 클래스 필드 레벨에 위치해야 합니다.
    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;
    
    // 이메일 폼이 요청될 GET 메서드 (예시)
    // @GetMapping("/email-form")
    // public String emailForm() {
    //     return "email_form";
    // }

    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {
        
        // 🚨 [누락된 try 블록 시작]
        try {
            // 1. 업로드 경로 설정 및 디렉토리 생성
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 2. 파일 이름 설정: 이메일에서 특수문자를 제거하고 .txt 파일 이름으로 사용
            String sanitizedEmail= email.replaceAll("[^a-zA-Z0-9]", "_");
            // 🚨 [오류 수정] filePath 설정 시 띄어쓰기 및 구문 오류 수정
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt"); 
            
            System.out.println("File path: " + filePath); 

            // 3. 파일 쓰기 (BufferedWriter를 try-with-resources 구문으로 안전하게 사용)
            // 🚨 [오류 수정] 띄어쓰기 및 구문 오류 수정
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일제목: " + subject); 
                writer.newLine(); 
                writer.write("요청메시지:");
                writer.newLine();
                writer.write(message);
            } // writer.close()는 try-with-resources에 의해 자동 호출됨

            // 4. 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
            
        // 🚨 [누락된 catch 블록 추가]
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
            }
        return "upload_end"; // .html 파일 연동
        }
        
} // 🚨 [클래스 종료]
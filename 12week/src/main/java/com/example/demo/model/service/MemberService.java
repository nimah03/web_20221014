// src/main/java/com/example/demo/model/service/MemberService.java
package com.example.demo.model.service;

import com.example.demo.model.domain.Member; // 🚨 [필수] Member 엔티티 import
import com.example.demo.model.repository.MemberRepository; // 🚨 [필수] Repository import
import com.example.demo.model.service.AddMemberRequest; // 🚨 [필수] DTO import



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 암호화
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 



@Service
@Transactional // 트랜잭션처리(클래스내모든메소드대상)
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 스프링버전5 이후, 단방향해싱알고리즘지원
    
    // 🚨 [필수] 회원가입 저장 메서드 (Controller에서 호출)
    public Long saveMember(AddMemberRequest request) {
        // 1. 이메일 중복 검사
        validateDuplicateMember(request); 

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // 3. DTO를 엔티티로 변환하고 암호화된 비밀번호를 설정
        Member member = request.toEntity();
        
        // 🚨 Member 엔티티에 Setter가 없기 때문에 Builder로 생성 후 비밀번호를 재설정하는 로직이 필요합니다.
        // 하지만 Member 엔티티의 @Builder 생성자에 password가 있으므로, 
        // DTO에서 toEntity()를 호출한 후 비밀번호만 바꿔주는 방법이 있습니다.
        // Member 엔티티에 @Setter가 없다면, 비밀번호를 변경하는 메서드를 추가해야 합니다.
        // 현재 Member 엔티티에 @Setter가 없으므로 임시로 toEntity()를 직접 사용하지 않겠습니다.
        
        // **[임시 수정]** toEntity 대신 Builder를 직접 사용하여 비밀번호를 암호화하여 저장
        Member newMember = Member.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(encodedPassword) // 🚨 암호화된 비밀번호 사용
            .age(request.getAge())
            .mobile(request.getMobile())
            .address(request.getAddress())
            .build();
        
        return memberRepository.save(newMember).getId();
    }
    
    private void validateDuplicateMember(AddMemberRequest request){
        Member findMember = memberRepository.findByEmail(request.getEmail()); // 이메일존재유무
        if(findMember!= null){
            throw new IllegalStateException("이미 가입된 회원입니다."); // 예외처리
        }
    }

    @Transactional(readOnly = true) // 조회 전용
    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email); // 이메일 조회
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }
        
        // 🚨 비밀번호 확인
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 암호화 비교
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member; // 인증 성공 시 회원 객체 반환
    }
} 
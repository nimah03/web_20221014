// src/main/java/com/example/demo/model/repository/MemberRepository.java
package com.example.demo.model.repository;

import com.example.demo.model.domain.Member; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // Member 객체를 반환하도록 선언되어 있으므로, Service에서 null 체크가 필요합니다.
    Member findByEmail(String email);
}
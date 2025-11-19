package com.example.demo.model.repository;

import com.example.demo.model.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 가입 시 이메일 중복 체크를 위한 메서드 [cite: 440]
    Member findByEmail(String email); // findByEmail은 전용 메소드입니다. [cite: 441]
}
package com.example.demo.service;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.AddMemberRequest;
import com.example.demo.model.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 기능
     */
    public Member saveMember(@Valid AddMemberRequest request) {
        validateDuplicateMember(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        return memberRepository.save(request.toEntity());
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(AddMemberRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /**
     * 로그인 체크 기능 (로그 추가됨!)
     */
    public Member loginCheck(String email, String rawPassword) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        // [로그] 입력받은 이메일 확인
        System.out.println("로그인 시도 이메일: " + email);

        if (member == null) {
            // [로그] 이메일 없음
            System.out.println("❌ 실패: 등록되지 않은 이메일입니다.");
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }

        // 2. 비밀번호 일치 확인
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            // [로그] 비밀번호 불일치
            System.out.println("❌ 실패: 비밀번호가 틀렸습니다.");
            System.out.println("입력한 비번: " + rawPassword);
            System.out.println("DB 암호화 비번: " + member.getPassword());
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // [로그] 성공
        System.out.println("✅ 로그인 성공! 환영합니다: " + member.getEmail());
        return member;
    }
}
package com.example.demo.service;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.AddMemberRequest;
import com.example.demo.model.repository.MemberRepository;
import jakarta.validation.Valid; // ★ [추가] 메서드 파라미터 검증용
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated; // ★ [추가] 클래스 레벨 검증용

@Service
@Validated // ★ [추가] 이 클래스의 메서드 호출 시 유효성 검사를 수행하겠다는 설정
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 기능
     * (@Valid 추가: 컨트롤러뿐만 아니라 서비스단에서도 들어오는 데이터의 유효성을 검사합니다)
     */
    public Member saveMember(@Valid AddMemberRequest request) { // ★ @Valid 추가 [cite: 771]
        validateDuplicateMember(request);

        // 비밀번호 암호화
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
     * 로그인 체크 기능
     */
    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email); // 이메일 조회 [cite: 708]

        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다."); // [cite: 710]
        }

        // 비밀번호 일치 확인 (matches 메서드: 평문 비번, 암호화된 비번 비교)
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // [cite: 711]
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // [cite: 713]
        }

        return member; // 인증 성공 시 회원 객체 반환 [cite: 714]
    }
}
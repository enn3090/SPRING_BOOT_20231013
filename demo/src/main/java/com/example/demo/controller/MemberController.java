package com.example.demo.controller;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.AddMemberRequest;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // --- [1] 회원가입 페이지 연결 ---
    @GetMapping("/join_new")
    public String join_new(Model model) {
        model.addAttribute("addMemberRequest", new AddMemberRequest());
        return "join_new";
    }

    // --- [2] 회원가입 요청 처리 ---
    @PostMapping("/api/members")
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "join_new";
        }

        try {
            memberService.saveMember(request);

            // (참고) 만약 회원가입 완료 화면(join_end.html)을 띄우고 싶다면
            // 아래 return "redirect:/login"; 을 return "join_end"; 로 바꾸시면 됩니다.
            return "redirect:/login";

        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "join_new";
        }
    }

    // --- [3] 로그인 페이지 연결 ---
    @GetMapping("/login")
    public String member_login() {
        return "login"; // templates/login.html 파일을 보여줌
    }

    // --- [4] 로그인 체크 요청 처리 (수정된 부분) ---
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model,
            HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false);

            // [수정] 기존 세션이 있다면 초기화만 수행 (쿠키 삭제 코드 제거함)
            if (session != null) {
                session.invalidate();
            }

            // 새로운 세션 생성 (서버가 자동으로 새 JSESSIONID 쿠키를 부여함)
            session = request2.getSession(true);

            // 서비스에서 로그인 검증 (비밀번호 불일치 시 여기서 에러 발생해서 catch로 넘어감)
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());

            String sessionId = UUID.randomUUID().toString();
            String email = request.getEmail();

            // 세션에 정보 저장
            session.setAttribute("userId", sessionId);
            session.setAttribute("email", email);

            model.addAttribute("member", member);

            // 로그인 성공 시 게시판 목록으로 이동
            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // --- [5] 로그아웃 요청 처리 ---
    @GetMapping("/api/logout")
    public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {

        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기

            if (session != null) {
                session.invalidate(); // 세션 초기화

                Cookie cookie = new Cookie("JSESSIONID", null); // 쿠키 삭제 준비
                cookie.setPath("/");
                cookie.setMaxAge(0); // 수명 0초
                response.addCookie(cookie); // 클라이언트로 쿠키 삭제 명령 전달
            }

            // 로그아웃 후 로그인 페이지로 이동
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
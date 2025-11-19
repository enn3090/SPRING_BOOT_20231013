package com.example.demo.controller;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.AddMemberRequest;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // --- [1] 회원가입 페이지 연결 (수정됨) ---
    @GetMapping("/join_new")
    public String join_new(Model model) {
        // ★ 수정 포인트: 폼과 연동될 빈 객체를 모델에 담아서 보냅니다.
        // HTML의 th:object="${addMemberRequest}"가 이 객체를 참조합니다.
        model.addAttribute("addMemberRequest", new AddMemberRequest());
        return "join_new";
    }

    // --- [2] 회원가입 요청 처리 ---
    @PostMapping("/api/members")
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request, BindingResult bindingResult,
            Model model) {

        // 1. 입력값 검증 에러가 있는지 확인 (예: 비밀번호 8자리 미만, 빈칸 등)
        if (bindingResult.hasErrors()) {
            // 에러가 있다면 다시 회원가입 페이지로 돌려보냄
            // (@ModelAttribute 덕분에 입력했던 request 값은 자동으로 유지됨)
            return "join_new";
        }

        try {
            memberService.saveMember(request);
            return "join_end"; // 성공 시 완료 페이지로

        } catch (IllegalStateException e) {
            // 2. 중복 이메일 등으로 인한 에러 처리
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "join_new"; // 다시 가입 페이지로
        }
    }

    // --- [3] 로그인 페이지 연결 ---
    @GetMapping("/member_login")
    public String member_login() {
        return "login";
    }

    // --- [4] 로그인 체크 요청 처리 ---
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model) {
        try {
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            model.addAttribute("member", member);
            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
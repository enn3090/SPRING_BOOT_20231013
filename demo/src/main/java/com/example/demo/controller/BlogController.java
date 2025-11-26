package com.example.demo.controller;

import com.example.demo.model.domain.Board;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.service.BlogService;
import jakarta.servlet.http.HttpSession; // 세션 처리용
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Board> articleList = blogService.findAll();
        model.addAttribute("articles", articleList);
        return "article_list";
    }

    // [메인] 게시판 목록 조회 (로그인 체크 포함)
    @GetMapping("/board_list")
    public String board_list(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            HttpSession session) {

        // 1. 로그인 여부 확인
        String userId = (String) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");

        if (userId == null) {
            return "redirect:/login"; // 로그인 안 했으면 쫓아냄
        }

        // 2. 게시글 페이징 및 검색 처리
        int pageSize = 3;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Board> list;

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        int startNum = (page * pageSize) + 1;

        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startNum", startNum);
        model.addAttribute("email", email); // 로그인한 사용자 이메일 전달

        return "board_list";
    }

    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    // [수정] 글 저장 (작성자 자동 저장 기능 추가)
    @PostMapping("/api/boards")
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        // 로그인한 사용자의 이메일을 가져옴
        String email = (String) session.getAttribute("email");

        // DTO에 작성자로 이메일을 세팅 (AddArticleRequest에 @Setter가 있어야 함)
        request.setUsername(email);

        blogService.save(request);
        return "redirect:/board_list";
    }

    // [수정] 글 상세 보기 (수정/삭제 버튼 권한 체크용 로그인 정보 전달)
    @GetMapping("/board_view/{id}")
    public String board_view(Model model, @PathVariable Long id, HttpSession session) {
        Optional<Board> list = blogService.findById(id);
        String email = (String) session.getAttribute("email"); // 현재 로그인한 사람

        if (list.isPresent()) {
            model.addAttribute("board", list.get());
            model.addAttribute("loginUser", email); // HTML로 로그인한 사람 정보 보냄
        } else {
            return "error_page/article_error";
        }
        return "board_view";
    }

    // 수정 페이지 이동
    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("board", list.get());
            return "board_edit";
        } else {
            return "error_page/article_error";
        }
    }

    // 글 수정 처리
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content) {
        blogService.update(id, title, content);
        return "redirect:/board_list";
    }

    // 글 삭제 처리
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    // 기타 메소드들
    @PostMapping("/api/articles")
    public String addArticle(AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list";
    }

    @GetMapping("/article_edit/{id}")
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("article", list.get());
            return "article_edit";
        } else {
            return "error_page/article_error";
        }
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, AddArticleRequest request) {
        blogService.update(id, request.getTitle(), request.getContent());
        return "redirect:/board_list";
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
    }
}
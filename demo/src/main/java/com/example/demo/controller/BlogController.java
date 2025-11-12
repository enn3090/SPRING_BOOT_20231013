package com.example.demo.controller;

import com.example.demo.model.domain.Board;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.service.BlogService;
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

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword) {

        int pageSize = 3; // [Slide 26] 한 페이지의 게시글 수 (3으로 설정)
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Board> list; // Page를 반환

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
        }

        int startNum = (page * pageSize) + 1; // [Slide 26] 시작 글번호 계산

        model.addAttribute("boards", list); // 모델에 추가
        model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
        model.addAttribute("currentPage", page); // 페이지 번호
        model.addAttribute("keyword", keyword); // 키워드
        model.addAttribute("startNum", startNum); // [Slide 26] 시작 글번호 전달

        return "board_list"; // .HTML 연결
    }

    // 글쓰기 게시판
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    @PostMapping("/api/articles")
    public String addArticle(AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list";
    }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
    public String addboards(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list"; // .HTML 연결
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
        blogService.update(id, request.getTitle(), request.getContent()); // Service 호출 방식도 변경
        return "redirect:/board_list";
    }

    @GetMapping("/board_view/{id}")
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("board", list.get());
        } else {
            return "/error_page/article_error";
        }
        return "board_view";
    }

    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("board", list.get());
            return "board_edit";
        } else {
            return "/error_page/article_error";
        }
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content) {

        blogService.update(id, title, content);

        return "redirect:/board_list";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
    }
}
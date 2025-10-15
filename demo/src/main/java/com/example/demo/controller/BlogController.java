package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // 게시글 목록 페이지 조회
    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> articleList = blogService.findAll();
        model.addAttribute("articles", articleList);
        return "article_list";
    }

    // 게시글 추가(생성) 처리
    @PostMapping("/api/articles")
    public String addArticle(AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/article_list";
    }

    // 게시글 수정 페이지 조회
    @GetMapping("/article_edit/{id}")
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id);

        if (list.isPresent()) {
            model.addAttribute("article", list.get());
            return "article_edit";
        } else {
            return "error_page/article_error";
        }
    }

    // 게시글 수정 처리
    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list";
    }

    // 게시글 삭제 처리
    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }

    // 파비콘(favicon.ico) 요청 오류 방지
    @GetMapping("/favicon.ico")
    public void favicon() {
        // 아무 작업도 하지 않음
    }
}
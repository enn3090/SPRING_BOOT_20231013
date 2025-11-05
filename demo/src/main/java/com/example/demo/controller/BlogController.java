package com.example.demo.controller;

import com.example.demo.model.domain.Board;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// 👇 @RequestParam을 사용하기 위해 import를 * (와일드카드)로 변경
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // ... (article_list, board_list, addArticle, board_view, deleteBoard 등은 동일) ...

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Board> articleList = blogService.findAll();
        model.addAttribute("articles", articleList);
        return "article_list";
    }

    @GetMapping("/board_list")
    public String board_list(Model model) {
        List<Board> list = blogService.findAll();
        model.addAttribute("boards", list);
        return "board_list";
    }

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

    /**
     * 👇 [500 오류 수정]
     * AddArticleRequest DTO 대신 @RequestParam으로 폼 데이터를 직접 받습니다.
     */
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content) {

        // 서비스로 DTO가 아닌 title, content 문자열을 직접 전달합니다.
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
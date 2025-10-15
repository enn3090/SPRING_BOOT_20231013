package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> articleList = blogService.findAll();
        model.addAttribute("articles", articleList);
        return "article_list";
    }
}
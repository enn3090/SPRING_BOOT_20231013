package com.example.demo.controller;

// import 문들을 모두 지웁니다.
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogRestController {

    // private final BlogService blogService; // 이 줄도 지웁니다.

    /*
     * @PostMapping("/api/articles")
     * public ResponseEntity<Article> addArticle(AddArticleRequest request) {
     * Article savedArticle = blogService.save(request);
     *
     * return ResponseEntity.status(HttpStatus.CREATED)
     * .body(savedArticle);
     * }
     */
}
package com.example.demo.model.service;

import com.example.demo.model.domain.Article;
import com.example.demo.model.dto.AddArticleRequest; // DTO를 사용하기 위한 import
import com.example.demo.model.repository.BlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 생성해 줍니다.
public class BlogService {

    private final BlogRepository blogRepository; // 생성자를 통한 의존성 주입

    /**
     * 모든 게시글을 조회하는 메서드
     * 
     * @return 게시글 리스트
     */
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    /**
     * 새로운 게시글을 저장하는 메서드
     * 
     * @param request 게시글의 제목과 내용이 담긴 DTO
     * @return 저장된 게시글 엔티티
     */
    public Article save(AddArticleRequest request) {
        // DTO를 엔티티 객체로 변환한 뒤, 리포지토리를 사용해 데이터베이스에 저장합니다.
        return blogRepository.save(request.toEntity());
    }
}
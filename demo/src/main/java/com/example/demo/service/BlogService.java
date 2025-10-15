package com.example.demo.service; // 실제 폴더 위치와 일치하는 올바른 주소입니다.

import com.example.demo.model.domain.Article;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.model.repository.BlogRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public Optional<Article> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void update(Long id, AddArticleRequest request) {
        Optional<Article> optionalArticle = blogRepository.findById(id);
        optionalArticle.ifPresent(article -> {
            article.update(request.getTitle(), request.getContent());
        });
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
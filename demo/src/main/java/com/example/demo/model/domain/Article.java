package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content = "";

    @Builder // 빌더 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 이 메서드를 Article 클래스 안에 추가하세요.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
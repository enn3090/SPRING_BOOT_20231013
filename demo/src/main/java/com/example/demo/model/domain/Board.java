package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // --- 👇 SQL 예약어를 피한 최종 필드 이름 ---

    @Column(name = "username", nullable = false)
    private String username; // 'user' 대신

    @Column(name = "newdate", nullable = false)
    private String newdate;

    @Column(name = "viewcount", nullable = false)
    private String viewcount; // 'count' 대신

    @Column(name = "likecount", nullable = false)
    private String likecount; // 'likec' 대신
    // --- 여기까지 수정 ---

    @Builder
    public Board(String title, String content, String username, String newdate, String viewcount, String likecount) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.newdate = newdate;
        this.viewcount = viewcount;
        this.likecount = likecount;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
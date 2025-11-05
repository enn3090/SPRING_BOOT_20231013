package com.example.demo.model.dto;

import com.example.demo.model.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // 👇 Board.java의 @Builder에 맞게 수정
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                // user, newdate, viewcount 등은 Board의 @Builder에서
                // "GUEST", "0" 같은 기본값으로 자동 처리됩니다.
                .build();
    }
}
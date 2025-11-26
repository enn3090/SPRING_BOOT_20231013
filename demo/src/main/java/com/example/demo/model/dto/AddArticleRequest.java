package com.example.demo.model.dto;

import com.example.demo.model.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddArticleRequest {

    private String title;
    private String content;

    // [수정] 필드명을 Board 엔티티에 맞춰 'username'으로 통일합니다.
    private String username;

    private String newdate;
    private String viewcount;
    private String likecount;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                // [수정] Board 엔티티의 빌더에 맞춰 .username()을 사용합니다.
                .username(username)
                .newdate(newdate)
                .viewcount(viewcount)
                .likecount(likecount)
                .build();
    }
}
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

    // 👇 [오류 수정] 필드명을 Board.java 엔티티와 일치시킵니다.
    private String username; // user -> username
    private String newdate;
    private String viewcount; // count -> viewcount
    private String likecount; // likec -> likecount

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                // 👇 [오류 수정] 빌더 메소드명도 엔티티와 일치시킵니다.
                .username(username) // user -> username
                .newdate(newdate)
                .viewcount(viewcount) // count -> viewcount
                .likecount(likecount) // likec -> likecount
                .build();
    }
}
package com.example.demo.service;

import com.example.demo.model.domain.Board;
import com.example.demo.model.dto.AddArticleRequest;
import com.example.demo.model.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional; // 올바른 import

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board save(AddArticleRequest request) {
        return boardRepository.save(request.toEntity());
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    /**
     * 👇 [500 오류 수정]
     * AddArticleRequest DTO 대신 title, content 문자열을 직접 받습니다.
     */
    @Transactional
    public void update(Long id, String title, String content) { // 👈 매개변수 변경
        Optional<Board> optionalBoard = boardRepository.findById(id);

        optionalBoard.ifPresent(board -> {
            board.update(
                    title, // (새 값)
                    content // (새 값)
            );
        });
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
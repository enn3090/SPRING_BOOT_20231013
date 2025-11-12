package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
// JpaRepository<Article, Long>가 아닌 JpaRepository<Board, Long>
public interface BoardRepository extends JpaRepository<Board, Long> {

    // (Slide 19) 검색 인터페이스 정의
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
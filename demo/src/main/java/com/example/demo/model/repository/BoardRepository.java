package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board; // 👈 1. Board 클래스를 import

@Repository
// 👇 2. 'Bord' -> 'Board' (오타 수정), 'Article' -> 'Board' (연동 객체 수정)
public interface BoardRepository extends JpaRepository<Board, Long> {
}
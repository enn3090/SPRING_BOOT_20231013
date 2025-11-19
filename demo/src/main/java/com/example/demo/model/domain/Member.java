package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member") // 테이블 이름은 member
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    @Column(name = "id", updatable = false) // 수정 x
    private Long id;

    @Column(name = "name", nullable = false) // null x
    private String name = "";

    @Column(name = "email", unique = true, nullable = false) // unique 중복 x, null x
    private String email = "";

    @Column(name = "password", nullable = false) // null x
    private String password = "";

    // --- 추가 필드 (강의 자료 12p) ---
    @Column(name = "age", nullable = false)
    private String age = "";

    @Column(name = "mobile", nullable = false)
    private String mobile = "";

    @Column(name = "address", nullable = false)
    private String address = "";

    // --- @Builder 생성자 (불변성) ---
    @Builder // 생성자에 빌더 패턴 적용(불변성)
    public Member(String name, String email, String password, String age, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}
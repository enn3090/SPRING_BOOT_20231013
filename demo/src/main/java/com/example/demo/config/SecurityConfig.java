package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// [수정] withDefaults 사용을 위한 static import 추가
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 스프링 설정 클래스 지정
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 헤더 설정 (XSS 방어 등)
                .headers(headers -> headers
                        .addHeaderWriter((request, response) -> {
                            response.setHeader("X-XSS-Protection", "1; mode=block"); // XSS 공격 감지 시 차단
                        }))

                // 2. CSRF 설정 (기본값 사용)
                .csrf(csrf -> csrf.disable()) // CSRF 보호 끄기 (토큰 없어도 됨)

                // 3. 세션 관리 설정 [핵심 수정 부분!]
                .sessionManagement(session -> session
                        // [수정] 세션이 지워지거나 만료되면 /login 페이지로 이동하도록 변경
                        // 기존: .invalidSessionUrl("/session-expired") (X)
                        .invalidSessionUrl("/login")
                        .maximumSessions(1) // 사용자 별 최대 세션 수 (1개만 허용)
                        .maxSessionsPreventsLogin(true) // 동시 로그인 차단 (기존 세션 유지, 신규 로그인 막음)
                )

                // 4. 인가(Authorization): 모든 요청 허용
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }

    /**
     * 비밀번호 암호화 설정
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 기본 사용자 생성 방지 설정
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
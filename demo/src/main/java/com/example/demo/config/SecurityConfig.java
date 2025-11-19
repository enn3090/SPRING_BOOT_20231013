package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 설정 클래스 지정
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig {

    /**
     * 보안 필터 체인(SecurityFilterChain) 설정
     * 모든 요청 허용, CSRF 비활성화, 기본 로그인 차단
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 인가(Authorization): 모든 요청(anyRequest)을 허용(permitAll)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                // 2. CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 3. 기본 폼 로그인 비활성화 (Spring Security 제공 로그인 페이지 제거)
                .formLogin(AbstractHttpConfigurer::disable)
                // 4. HTTP Basic 인증 비활성화 (브라우저 팝업 인증 제거)
                .httpBasic(AbstractHttpConfigurer::disable);

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
     * [핵심] 기본 사용자 생성 방지 설정
     * 이 Bean을 등록하면 콘솔의 "Using generated security password" 메시지가 사라집니다.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // 비어있는 매니저를 반환하여 Spring이 제멋대로 임시 유저를 만들지 못하게 함
        return new InMemoryUserDetailsManager();
    }
}
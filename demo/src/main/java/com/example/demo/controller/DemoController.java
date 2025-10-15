package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 1. import 경로 및 클래스 이름 수정
import com.example.demo.model.domain.TestDB; // 'domain' 경로 추가
import com.example.demo.service.TestService;

import lombok.RequiredArgsConstructor;

/**
 * 데모용 웹 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어주는 Lombok 어노테이션
public class DemoController {

    // 2. @Autowired 대신 생성자 주입 방식으로 변경 (최신 권장 방식)
    private final TestService testService;

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "반갑습니다.");
        return "hello";
    }

    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 반갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", "01");
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", "002");
        return "thymeleaf_test1";
    }

    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        // 3. testService 변수와 TestDB 타입을 올바르게 사용
        TestDB test = testService.findByName("홍길동");
        model.addAttribute("data4", test);
        System.out.println("데이터 출력 디버그 : " + test);
        return "testdb";
    }

}
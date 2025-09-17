package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 데모용 웹 요청을 처리하는 컨트롤러 클래스
 */
@Controller
public class DemoController {

    @GetMapping("/hello")
    public String hello(Model model) {
        // Model 객체에 'data'라는 이름으로 환영 메시지를 추가합니다.
        // 이 데이터는 뷰(hello.html)에서 사용됩니다.
        model.addAttribute("data", "반갑습니다.");

        // 뷰 리졸버(View Resolver)가 'hello.html' 템플릿을 찾아 렌더링합니다.
        return "hello";
    }

    // 상세 소개 페이지를 위한 새로운 경로 매핑
    @GetMapping("/about_detailed")
    public String about() {
        // templates 폴더의 about_detailed.html 파일을 찾아 리턴합니다.
        return "about_detailed";
    }
}
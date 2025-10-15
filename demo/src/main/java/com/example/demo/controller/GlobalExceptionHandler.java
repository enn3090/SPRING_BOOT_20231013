package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice // 이 클래스가 모든 컨트롤러에 대한 전역 예외 처리를 담당함을 선언
public class GlobalExceptionHandler {

    /**
     * URL의 파라미터 타입이 일치하지 않을 때 발생하는 예외를 처리합니다.
     * (예: 숫자여야 하는 ID에 문자가 들어왔을 경우)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatchException(Model model, MethodArgumentTypeMismatchException ex) {
        // 에러 페이지에 전달할 메시지를 모델에 추가할 수 있습니다.
        model.addAttribute("errorMessage", "잘못된 형식의 URL입니다. ID는 숫자여야 합니다.");

        // 이전에 만든 에러 페이지를 보여줍니다.
        return "error_page/article_error";
    }
}
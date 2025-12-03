package com.example.demo.controller; // 패키지명은 본인 프로젝트 설정에 맞게 조정

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    // application.properties에 등록된 설정(경로) 주입 [cite: 156]
    @Value("${spring.servlet.multipart.location}")
    private String uploadFolder;

    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음 [cite: 158]
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        try {
            // 업로드 경로 설정 및 폴더 생성 [cite: 164, 165, 166]
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이메일 주소에서 특수문자를 제거하여 파일명으로 사용 [cite: 183]
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt"); // 업로드 폴더에 .txt 이름 설정 [cite: 184]
            System.out.println("File path: " + filePath); // 디버깅용 출력 [cite: 184]

            // 파일 쓰기 (BufferedWriter 사용) [cite: 185]
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일 제목: " + subject); // 제목 쓰기 [cite: 186]
                writer.newLine(); // 줄 바꿈 [cite: 187]
                writer.write("요청 메시지: "); // 메시지 라벨 [cite: 188]
                writer.newLine();
                writer.write(message); // 메시지 내용 [cite: 190]
            }

            // 성공 메시지 전달 [cite: 196]
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");

        } catch (IOException e) {
            e.printStackTrace();
            // 오류 발생 시 메시지 전달 및 에러 페이지로 이동 [cite: 205]
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "/error_page/article_error";
        }

        return "upload_end"; // .html 파일 연동 (성공 시) [cite: 207]
    }
}
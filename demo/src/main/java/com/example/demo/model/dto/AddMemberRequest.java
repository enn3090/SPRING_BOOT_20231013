package com.example.demo.model.dto;

import com.example.demo.model.domain.Member;
import jakarta.validation.constraints.*; // 검증 어노테이션 import
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "비밀번호는 8자 이상, 대소문자를 포함해야 합니다.")
    private String password;

    @NotNull(message = "나이는 필수 입력 값입니다.")
    @Min(value = 19, message = "나이는 19세 이상이어야 합니다.")
    @Max(value = 90, message = "나이는 90세 이하여야 합니다.")
    private Integer age; // String -> Integer로 변경 (숫자 범위 체크를 위해)

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String mobile;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(String.valueOf(age)) // DB에는 String으로 저장된다면 변환 필요
                .mobile(mobile)
                .address(address)
                .build();
    }
}
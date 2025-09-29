package com.example.demo.model.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository; // 👈 @Autowired 없이 final로 선언

    public TestDB findByName(String name) {
        // 불필요한 형변환 제거
        return testRepository.findByName(name);
    }
}
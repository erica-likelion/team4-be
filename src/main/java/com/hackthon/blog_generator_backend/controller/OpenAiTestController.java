package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.service.BlogGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test/openai")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OpenAiTestController {

    private final BlogGenerationService blogGenerationService;

    @PostMapping("/generate-content")
    public ResponseEntity<Map<String, Object>> testGenerateContent(
            @RequestBody Map<String, String> request) {
        
        String userInput = request.get("userInput");
        String storeInfo = request.get("storeInfo");
        
        if (userInput == null || userInput.trim().isEmpty()) {
            userInput = "맛있는 커피와 디저트를 즐길 수 있는 카페";
        }
        
        if (storeInfo == null || storeInfo.trim().isEmpty()) {
            storeInfo = "서울 강남구에 위치한 분위기 좋은 카페";
        }
        
        log.info("OpenAI API 테스트 요청: userInput={}, storeInfo={}", userInput, storeInfo);
        
        try {
            String response = blogGenerationService.generateBlogContent(userInput, storeInfo);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("userInput", userInput);
            result.put("storeInfo", storeInfo);
            result.put("generatedContent", response);
            result.put("timestamp", System.currentTimeMillis());
            
            log.info("OpenAI API 테스트 성공");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("OpenAI API 테스트 실패: {}", e.getMessage(), e);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("/generate-title")
    public ResponseEntity<Map<String, Object>> testGenerateTitle(
            @RequestBody Map<String, String> request) {
        
        String userInput = request.get("userInput");
        
        if (userInput == null || userInput.trim().isEmpty()) {
            userInput = "맛있는 커피와 디저트를 즐길 수 있는 카페";
        }
        
        log.info("OpenAI API 제목 생성 테스트 요청: userInput={}", userInput);
        
        try {
            String title = blogGenerationService.generateBlogTitle(userInput);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("userInput", userInput);
            result.put("generatedTitle", title);
            result.put("timestamp", System.currentTimeMillis());
            
            log.info("OpenAI API 제목 생성 테스트 성공: {}", title);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("OpenAI API 제목 생성 테스트 실패: {}", e.getMessage(), e);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkOpenAiHealth() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "OK");
        result.put("service", "OpenAI API Test Controller");
        result.put("timestamp", System.currentTimeMillis());
        result.put("message", "OpenAI API 테스트 컨트롤러가 정상적으로 동작 중입니다.");
        
        return ResponseEntity.ok(result);
    }
}






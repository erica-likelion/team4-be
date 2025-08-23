package com.hackthon.blog_generator_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class SimpleTestController {

    @GetMapping("/test/simple")
    public Map<String, Object> simpleTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "간단한 테스트 성공!");
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", "OK");
        return response;
    }

    @GetMapping("/test/openai-key")
    public Map<String, Object> testOpenAiKey() {
        Map<String, Object> response = new HashMap<>();
        
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            response.put("hasApiKey", true);
            response.put("apiKeyPrefix", apiKey.substring(0, Math.min(20, apiKey.length())) + "...");
            response.put("message", "OpenAI API 키가 설정되어 있습니다.");
        } else {
            response.put("hasApiKey", false);
            response.put("message", "OpenAI API 키가 설정되지 않았습니다.");
        }
        
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}

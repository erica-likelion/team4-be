package com.hackthon.blog_generator_backend.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
@Slf4j
public class OpenAiConfig {

    @Value("${openai.api.key:}")
    private String apiKey;

    @Bean
    public OpenAiService openAiService() {
        log.info("OpenAI 서비스 빈 생성 시작...");
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.error("OpenAI API 키가 설정되지 않았습니다. 환경변수 OPENAI_API_KEY를 확인해주세요.");
            throw new IllegalStateException("OpenAI API key is not configured. Please set 'openai.api.key' property or OPENAI_API_KEY environment variable.");
        }
        
        if ("your-openai-api-key-here".equals(apiKey.trim())) {
            log.error("OpenAI API 키가 기본값으로 설정되어 있습니다. 실제 API 키를 입력해주세요.");
            throw new IllegalStateException("OpenAI API key is set to default value. Please provide actual API key.");
        }
        
        // API 키 마스킹 (보안을 위해)
        String maskedKey = apiKey.length() > 8 ? 
            apiKey.substring(0, 4) + "..." + apiKey.substring(apiKey.length() - 4) : 
            "***";
        
        log.info("OpenAI API 키 검증 완료. 마스킹된 키: {}", maskedKey);
        
        try {
            // 타임아웃 설정 (60초)
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));
            log.info("OpenAI 서비스 빈 생성 성공 (타임아웃: 60초)");
            return service;
        } catch (Exception e) {
            log.error("OpenAI 서비스 생성 중 오류 발생: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to create OpenAI service: " + e.getMessage(), e);
        }
    }
}

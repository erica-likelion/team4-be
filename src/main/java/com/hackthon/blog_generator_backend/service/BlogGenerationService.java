package com.hackthon.blog_generator_backend.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogGenerationService {

    private final OpenAiService openAiService;

    public String generateBlogContent(String userInput, String storeInfo) {
        log.info("블로그 콘텐츠 생성 요청: userInput={}, storeInfo={}", userInput, storeInfo);
        
        try {
            String prompt = createBlogPrompt(userInput, storeInfo);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            String response = openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();

            log.info("OpenAI API 응답 성공: 길이={}", response.length());
            return response;

        } catch (Exception e) {
            log.error("OpenAI API 호출 중 오류 발생: {}", e.getMessage(), e);
            // API 실패 시 Mock 응답으로 fallback
            return generateFallbackResponse(userInput, storeInfo, e.getMessage());
        }
    }

    public String generateBlogTitle(String userInput) {
        log.info("블로그 제목 생성 요청: userInput={}", userInput);
        
        try {
            String prompt = createTitlePrompt(userInput);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .maxTokens(100)
                    .temperature(0.8)
                    .build();

            String response = openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();

            log.info("OpenAI API 제목 생성 성공: {}", response);
            return response.trim();

        } catch (Exception e) {
            log.error("제목 생성 중 오류 발생: {}", e.getMessage(), e);
            return String.format("매력적인 %s 블로그 포스트", 
                userInput.length() > 10 ? userInput.substring(0, 10) + "..." : userInput);
        }
    }

    private String createBlogPrompt(String userInput, String storeInfo) {
        return String.format("""
            다음 정보를 바탕으로 매력적인 블로그 포스트를 작성해주세요:
            
            사용자 입력: %s
            매장 정보: %s
            
            요구사항:
            1. 제목: 매력적이고 SEO 친화적인 제목 (50자 이내)
            2. 내용: 300-500자 내외의 자연스러운 블로그 포스트
            3. 해시태그: 관련된 해시태그 3-5개
            4. 톤앤매너: 친근하고 신뢰할 수 있는 톤
            
            반드시 다음 JSON 형식으로만 응답해주세요:
            {
              "title": "제목",
              "content": "내용",
              "hashtags": ["해시태그1", "해시태그2", "해시태그3"]
            }
            """, userInput, storeInfo);
    }

    private String createTitlePrompt(String userInput) {
        return String.format("""
            다음 사용자 입력을 바탕으로 매력적인 블로그 제목을 생성해주세요:
            
            사용자 입력: %s
            
            요구사항:
            - 50자 이내
            - SEO 친화적
            - 클릭을 유도하는 제목
            - 해시태그 포함
            
            제목만 응답해주세요. (JSON 형식 없이 제목만)
            """, userInput);
    }

    private String generateFallbackResponse(String userInput, String storeInfo, String errorMessage) {
        log.warn("OpenAI API 실패로 인한 Fallback 응답 생성");
        
        String title = String.format("매력적인 %s 블로그 포스트", 
            userInput.length() > 10 ? userInput.substring(0, 10) + "..." : userInput);
        
        String content = String.format("""
            사용자 입력: %s
            
            매장 정보: %s
            
            이 정보를 바탕으로 작성된 블로그 포스트입니다. 
            현재 AI 서비스에 일시적인 문제가 발생하여 기본 응답을 제공합니다.
            
            실제 서비스에서는 AI가 이 정보를 바탕으로 창의적이고 매력적인 
            블로그 포스트를 생성할 것입니다.
            
            오류 정보: %s
            """, userInput, storeInfo, errorMessage);
        
        return String.format("""
            {
              "title": "%s",
              "content": "%s",
              "hashtags": ["블로그", "AI", "자동생성", "fallback"]
            }
            """, title, content.replace("\"", "\\\"").replace("\n", "\\n"));
    }
}
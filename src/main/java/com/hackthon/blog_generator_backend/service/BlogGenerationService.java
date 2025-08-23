package com.hackthon.blog_generator_backend.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogGenerationService {

    private final OpenAiService openAiService;
    
    @Value("${openai.api.key:}")
    private String apiKey;

    @Value("${openai.model:gpt-4o}")
    private String modelName;

    public String generateBlogContent(String userInput, String storeInfo) {
        log.info("블로그 콘텐츠 생성 요청: userInput={}, storeInfo={}", userInput, storeInfo);
        
        // API 키 확인
        if (!isApiKeyValid()) {
            log.error("OpenAI API 키가 유효하지 않습니다.");
            throw new IllegalStateException("OpenAI API 키가 설정되지 않았습니다.");
        }
        
        try {
            String prompt = createBlogPrompt(userInput, storeInfo);
            log.debug("생성된 프롬프트: {}", prompt);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(modelName)
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            log.info("OpenAI API 호출 시작...");
            String response = openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();

            log.info("OpenAI API 응답 성공: 길이={}", response.length());
            log.debug("API 응답 내용: {}", response);
            return response;

        } catch (Exception e) {
            log.error("OpenAI API 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("블로그 콘텐츠 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public String generateBlogTitle(String userInput) {
        log.info("블로그 제목 생성 요청: userInput={}", userInput);
        
        // API 키 확인
        if (!isApiKeyValid()) {
            log.error("OpenAI API 키가 유효하지 않습니다.");
            throw new IllegalStateException("OpenAI API 키가 설정되지 않았습니다.");
        }
        
        try {
            String prompt = createTitlePrompt(userInput);
            log.debug("생성된 제목 프롬프트: {}", prompt);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(modelName)
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .maxTokens(100)
                    .temperature(0.8)
                    .build();

            log.info("OpenAI API 제목 생성 호출 시작...");
            String response = openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();

            log.info("OpenAI API 제목 생성 성공: {}", response);
            return response.trim();

        } catch (Exception e) {
            log.error("제목 생성 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("블로그 제목 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    private boolean isApiKeyValid() {
        return apiKey != null && !apiKey.trim().isEmpty() && 
               !"your-openai-api-key-here".equals(apiKey.trim());
    }

    private String createBlogPrompt(String userInput, String storeInfo) {
        return String.format("""
            당신은 전문 블로그 작가입니다. 다음 정보를 바탕으로 독자들이 꼭 방문하고 싶어하는 매력적인 블로그 포스트를 작성해주세요.
            
            [입력 정보]
            사용자 요청: %s
            매장/서비스 정보: %s
            
            [작성 요구사항]
            1. 제목: 
               - 클릭을 유도하는 매력적인 제목 (50자 이내)
               - 감정을 자극하는 표현 사용
               - 구체적인 장소나 특징 포함
            
            2. 내용 구조:
               - 도입부: 왜 이곳을 방문해야 하는지 (충분한 설명)
               - 본문: 구체적인 특징과 경험담을 상세하게 서술
                 * 위치와 접근성에 대한 자세한 설명
                 * 인테리어와 분위기의 생생한 묘사
                 * 메뉴와 음식의 특징과 맛에 대한 상세한 설명
                 * 서비스와 편의시설에 대한 구체적인 정보
                 * 방문 경험담과 추천 포인트를 풍부하게 서술
                 * 주변 환경과 분위기에 대한 묘사
                 * 방문하기 좋은 시간대나 계절에 대한 조언
               - 결론: 방문을 독려하는 마무리 (충분한 설득력)
               - 총 1000-2000자 내외 (매우 상세하고 풍부한 내용)
            
            3. 톤앤매너:
               - 친근하고 신뢰할 수 있는 톤
               - 감정적이고 공감되는 표현
               - 구체적인 경험과 느낌을 담은 서술
               - 독자들이 실제 방문한 것처럼 생생한 묘사
               - 각 문장이 독자에게 새로운 정보나 감동을 전달하도록 구성
            
            4. 해시태그: 
               - 최소 5개 이상의 관련 해시태그 생성
               - 위치, 음식/서비스, 분위기, 감정 등을 포함
               - 한국어와 영어 혼용 가능
               - 예시: #강남카페 #스타벅스 #아늑한분위기 #커피맛집 #데이트장소
            
            5. 한국어로 작성
            
            [출력 형식]
            JSON 형태로 다음 필드를 포함하여 응답해주세요:
            {
              "title": "생성된 제목",
              "content": "생성된 블로그 내용",
              "hashtags": ["해시태그1", "해시태그2", "해시태그3", "해시태그4", "해시태그5"]
            }
            """, userInput, storeInfo);
    }

    private String createTitlePrompt(String userInput) {
        return String.format("""
            당신은 전문 블로그 제목 작성자입니다. 다음 요청을 바탕으로 클릭을 유도하는 매력적인 제목을 생성해주세요.
            
            [사용자 요청]
            %s
            
            [제목 요구사항]
            1. 길이: 50자 이내
            2. 스타일: 클릭을 유도하는 매력적인 표현
            3. 감정: 호기심을 자극하고 방문하고 싶게 만드는 표현
            4. 구체성: 장소, 특징, 감정 등을 구체적으로 포함
            5. 한국어로 작성
            6. 해시태그 포함 가능
            7. 감탄사나 강조 표현 사용 가능 (예: "최고의", "특별한", "완벽한")
            
            [출력 형식]
            JSON 형태로 응답해주세요:
            {
              "title": "생성된 제목"
            }
            """, userInput);
    }
}
package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.dto.MadeRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeResponseDto;
import com.hackthon.blog_generator_backend.dto.UserInputRequestDto;
import com.hackthon.blog_generator_backend.entity.Made;
import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.exception.ResourceNotFoundException;
import com.hackthon.blog_generator_backend.repository.MadeRepository;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MadeService {
    
    private final MadeRepository madeRepository;
    private final StoreRepository storeRepository;
    private final BlogGenerationService blogGenerationService;
    
    // Made 생성 (POST)
    @Transactional
    public MadeResponseDto createMade(MadeRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", requestDto.getStoreId()));
        
        Made made = Made.builder()
                .image(requestDto.getImage())
                .hashTag(requestDto.getHashTag())
                .prompt(requestDto.getPrompt())
                .userInput(requestDto.getUserInput())
                .resultTitle(requestDto.getResultTitle())
                .resultContent(requestDto.getResultContent())
                .store(store)
                .build();
        
        Made savedMade = madeRepository.save(made);
        
        return convertToResponseDto(savedMade);
    }
    
    // 결과 조회 (GET)
    public MadeResponseDto getResult(Long madeId) {
        Made made = madeRepository.findById(madeId)
                .orElseThrow(() -> new ResourceNotFoundException("Made", madeId));
        
        return convertToResponseDto(made);
    }
    
    // 사용자 입력 처리 및 AI 블로그 생성 (POST)
    @Transactional
    public MadeResponseDto processUserInput(UserInputRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", requestDto.getStoreId()));
        
        try {
            // AI 블로그 콘텐츠 생성 (현재는 Mock 응답)
            String storeInfo = String.format("매장명: %s, 위치: %s, 정보: %s", 
                store.getStoreName(), store.getLocation(), store.getInformation());
            
            String aiResponse = blogGenerationService.generateBlogContent(
                requestDto.getUserInput(), storeInfo);
            
            // 간단한 JSON 파싱 (실제로는 더 정교한 파싱이 필요)
            String generatedTitle = extractTitleFromResponse(aiResponse);
            String generatedContent = extractContentFromResponse(aiResponse);
            String hashtags = extractHashtagsFromResponse(aiResponse);
            
            // Made 엔티티 생성 및 저장
            Made made = Made.builder()
                    .userInput(requestDto.getUserInput())
                    .prompt(requestDto.getPrompt())
                    .store(store)
                    .resultTitle(generatedTitle)
                    .resultContent(generatedContent)
                    .hashTag(hashtags)
                    .build();
            
            Made savedMade = madeRepository.save(made);
            log.info("AI 블로그 생성 완료: {}", generatedTitle);
            
            return convertToResponseDto(savedMade);
            
        } catch (Exception e) {
            log.error("블로그 생성 중 오류: {}", e.getMessage());
            throw new RuntimeException("블로그 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // Made 목록 조회
    public List<MadeResponseDto> getAllMade() {
        List<Made> madeList = madeRepository.findAll();
        return madeList.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    // DTO 변환 메서드
    private MadeResponseDto convertToResponseDto(Made made) {
        // Store 정보 안전하게 처리
        Long storeId = null;
        String storeName = null;
        
        if (made.getStore() != null) {
            storeId = made.getStore().getStoreId();
            storeName = made.getStore().getStoreName();
        }
        
        return MadeResponseDto.builder()
                .madeId(made.getMadeId())
                .image(made.getImage())
                .hashTag(made.getHashTag())
                .prompt(made.getPrompt())
                .userInput(made.getUserInput())
                .resultTitle(made.getResultTitle())
                .resultContent(made.getResultContent())
                .storeId(storeId)
                .storeName(storeName)
                .createdAt(made.getCreatedAt())
                .build();
    }
    
    // 간단한 JSON 파싱 메서드들
    private String extractTitleFromResponse(String response) {
        if (response.contains("\"title\":")) {
            int start = response.indexOf("\"title\":") + 9;
            int end = response.indexOf("\"", start);
            if (end > start) {
                return response.substring(start, end).trim();
            }
        }
        return "제목을 찾을 수 없습니다";
    }
    
    private String extractContentFromResponse(String response) {
        if (response.contains("\"content\":")) {
            int start = response.indexOf("\"content\":") + 11;
            int end = response.indexOf("\"", start);
            if (end > start) {
                return response.substring(start, end).trim();
            }
        }
        return "내용을 찾을 수 없습니다";
    }
    
    private String extractHashtagsFromResponse(String response) {
        if (response.contains("\"hashtags\":")) {
            int start = response.indexOf("\"hashtags\":");
            int end = response.indexOf("]", start);
            if (end > start) {
                return response.substring(start, end + 1);
            }
        }
        return "[]";
    }
}

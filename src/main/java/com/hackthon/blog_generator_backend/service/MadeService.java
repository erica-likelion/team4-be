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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

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
    
    // 사진 업로드 및 블로그 생성 (POST)
    @Transactional
    public MadeResponseDto createMadeWithImages(Long storeId, String userInput, String hashTags, 
                                             String detailedRequest, List<MultipartFile> images) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", storeId));
        
        try {
            // 이미지 처리 (실제로는 파일 저장 로직 필요)
            String imageUrls = processImages(images);
            
            // 가게 정보를 자동으로 프롬프트에 포함
            String storeInfo = String.format("매장명: %s, 위치: %s, 영업시간: %s, 정보: %s, 메뉴: %s", 
                store.getStoreName(), store.getLocation(), store.getStoreTime(), 
                store.getInformation(), store.getMenu());
            
            // 해시태그를 프롬프트에 포함
            String enhancedUserInput = userInput;
            if (hashTags != null && !hashTags.trim().isEmpty()) {
                enhancedUserInput += "\n\n해시태그: " + hashTags;
            }
            
            // 상세 요청을 프롬프트에 포함
            if (detailedRequest != null && !detailedRequest.trim().isEmpty()) {
                enhancedUserInput += "\n\n상세 요청: " + detailedRequest;
            }
            
            // AI 블로그 콘텐츠 생성
            String aiResponse = blogGenerationService.generateBlogContent(enhancedUserInput, storeInfo);
            
            String generatedTitle = extractTitleFromResponse(aiResponse);
            String generatedContent = extractContentFromResponse(aiResponse);
            String extractedHashtags = extractHashtagsFromResponse(aiResponse);
            
            // Made 엔티티 생성 및 저장
            Made made = Made.builder()
                    .userInput(userInput)
                    .prompt(enhancedUserInput)
                    .store(store)
                    .resultTitle(generatedTitle)
                    .resultContent(generatedContent)
                    .hashTag(extractedHashtags)
                    .image(imageUrls)
                    .build();
            
            Made savedMade = madeRepository.save(made);
            log.info("이미지 포함 블로그 생성 완료: {}", generatedTitle);
            
            return convertToResponseDto(savedMade);
            
        } catch (Exception e) {
            log.error("이미지 포함 블로그 생성 중 오류: {}", e.getMessage());
            throw new RuntimeException("블로그 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 블로그 내용 수정 (POST)
    @Transactional
    public MadeResponseDto modifyBlogContent(Long madeId, String modificationRequest) {
        Made made = madeRepository.findById(madeId)
                .orElseThrow(() -> new ResourceNotFoundException("Made", madeId));
        
        try {
            // 기존 내용과 수정 요청을 결합
            String originalContent = made.getResultContent();
            String modificationPrompt = String.format(
                "다음 블로그 내용을 수정해주세요:\n\n" +
                "원본 내용:\n%s\n\n" +
                "수정 요청:\n%s\n\n" +
                "수정된 내용을 한국어로 작성하고, 해시태그도 포함해주세요.",
                originalContent, modificationRequest
            );
            
            // AI를 통한 내용 수정
            String aiResponse = blogGenerationService.generateBlogContent(modificationPrompt, "");
            
            String modifiedTitle = extractTitleFromResponse(aiResponse);
            String modifiedContent = extractContentFromResponse(aiResponse);
            String modifiedHashtags = extractHashtagsFromResponse(aiResponse);
            
            // Made 엔티티 업데이트
            made.setResultTitle(modifiedTitle);
            made.setResultContent(modifiedContent);
            made.setHashTag(modifiedHashtags);
            
            Made updatedMade = madeRepository.save(made);
            log.info("블로그 내용 수정 완료: {}", modifiedTitle);
            
            return convertToResponseDto(updatedMade);
            
        } catch (Exception e) {
            log.error("블로그 내용 수정 중 오류: {}", e.getMessage());
            throw new RuntimeException("블로그 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 이미지 처리 메서드 (실제로는 파일 저장 로직 필요)
    private String processImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return "";
        }
        
        // 실제로는 이미지를 서버에 저장하고 URL을 반환해야 함
        // 현재는 간단하게 파일명만 반환
        return images.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(MultipartFile::getOriginalFilename)
                .filter(name -> name != null && !name.isEmpty())
                .collect(Collectors.joining(","));
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
        try {
            // JSON 응답에서 title 필드 추출
            if (response.contains("\"title\":")) {
                int start = response.indexOf("\"title\":") + 9;
                int end = response.indexOf("\"", start);
                if (end > start) {
                    return response.substring(start, end).trim();
                }
            }
            
            // 대안: JSON 파싱 시도
            if (response.startsWith("{")) {
                try {
                    // Jackson을 사용한 JSON 파싱
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response);
                    if (jsonNode.has("title")) {
                        return jsonNode.get("title").asText();
                    }
                } catch (Exception e) {
                    log.warn("Jackson JSON 파싱 실패, 정규식으로 추출 시도: {}", e.getMessage());
                }
            }
            
            return "제목을 찾을 수 없습니다";
        } catch (Exception e) {
            log.error("제목 추출 중 오류: {}", e.getMessage());
            return "제목을 찾을 수 없습니다";
        }
    }
    
    private String extractContentFromResponse(String response) {
        try {
            // JSON 응답에서 content 필드 추출
            if (response.contains("\"content\":")) {
                int start = response.indexOf("\"content\":") + 11;
                int end = response.indexOf("\"", start);
                if (end > start) {
                    return response.substring(start, end).trim();
                }
            }
            
            // 대안: JSON 파싱 시도
            if (response.startsWith("{")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response);
                    if (jsonNode.has("content")) {
                        return jsonNode.get("content").asText();
                    }
                } catch (Exception e) {
                    log.warn("Jackson JSON 파싱 실패, 정규식으로 추출 시도: {}", e.getMessage());
                }
            }
            
            return "내용을 찾을 수 없습니다";
        } catch (Exception e) {
            log.error("내용 추출 중 오류: {}", e.getMessage());
            return "내용을 찾을 수 없습니다";
        }
    }
    
    private String extractHashtagsFromResponse(String response) {
        try {
            // JSON 응답에서 hashtags 필드 추출
            if (response.contains("\"hashtags\":")) {
                int start = response.indexOf("\"hashtags\":");
                int end = response.indexOf("]", start);
                if (end > start) {
                    return response.substring(start, end + 1);
                }
            }
            
            // 대안: JSON 파싱 시도
            if (response.startsWith("{")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response);
                    if (jsonNode.has("hashtags")) {
                        JsonNode hashtagsNode = jsonNode.get("hashtags");
                        if (hashtagsNode.isArray()) {
                            return hashtagsNode.toString();
                        }
                    }
                } catch (Exception e) {
                    log.warn("Jackson JSON 파싱 실패, 정규식으로 추출 시도: {}", e.getMessage());
                }
            }
            
            return "[]";
        } catch (Exception e) {
            log.error("해시태그 추출 중 오류: {}", e.getMessage());
            return "[]";
        }
    }
}

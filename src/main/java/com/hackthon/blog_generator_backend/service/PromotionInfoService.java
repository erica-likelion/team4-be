package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.dto.promotion.PromotionCreateRequestDto;
import com.hackthon.blog_generator_backend.dto.promotion.PromotionDetailResponseDto;
import com.hackthon.blog_generator_backend.dto.promotion.PromotionListResponseDto;
import com.hackthon.blog_generator_backend.entity.PromotionInfo;
import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.repository.PromotionInfoRepository;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionInfoService {
    
    private final PromotionInfoRepository promotionInfoRepository;
    private final StoreRepository storeRepository;
    
    /**
     * 전체 프로모션 목록 조회 (최신순)
     * @return PromotionListResponseDto 리스트
     */
    public List<PromotionListResponseDto> getAllPromotions() {
        List<PromotionInfo> promotions = promotionInfoRepository.findAllWithStoreOrderByDateDesc();
        
        return promotions.stream()
                .map(this::convertToListResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 프로모션 상세 정보 조회
     * @param promotionId 프로모션 ID
     * @return PromotionDetailResponseDto
     */
    public PromotionDetailResponseDto getPromotionDetail(Long promotionId) {
        PromotionInfo promotion = promotionInfoRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("프로모션을 찾을 수 없습니다. ID: " + promotionId));
        
        return convertToDetailResponseDto(promotion);
    }
    
    /**
     * 새 프로모션 등록
     * @param request 프로모션 등록 요청 DTO
     * @return PromotionDetailResponseDto
     */
    @Transactional
    public PromotionDetailResponseDto createPromotion(PromotionCreateRequestDto request) {
        // 연관된 Store 조회
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다. ID: " + request.getStoreId()));
        
        // PromotionInfo 엔티티 생성
        PromotionInfo promotion = PromotionInfo.builder()
                .title(request.getTitle())
                .image(request.getImage())
                .description(request.getDescription())
                .date(LocalDate.now()) // 현재 날짜로 자동 설정
                .likeCount(0) // 초기값
                .comment(request.getComment())
                .prompt(request.getPrompt())
                .store(store)
                .build();
        
        // 저장
        PromotionInfo savedPromotion = promotionInfoRepository.save(promotion);
        
        // DTO로 변환 후 반환
        return convertToDetailResponseDto(savedPromotion);
    }
    
    /**
     * 프로모션 좋아요 수 증가
     * @param promotionId 프로모션 ID
     * @return 업데이트된 PromotionDetailResponseDto
     */
    @Transactional
    public PromotionDetailResponseDto increaseLikeCount(Long promotionId) {
        PromotionInfo promotion = promotionInfoRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("프로모션을 찾을 수 없습니다. ID: " + promotionId));
        
        promotion.setLikeCount(promotion.getLikeCount() + 1);
        
        return convertToDetailResponseDto(promotion);
    }
    
    /**
     * 특정 가게의 프로모션 목록 조회
     * @param storeId 가게 ID
     * @return PromotionListResponseDto 리스트
     */
    public List<PromotionListResponseDto> getPromotionsByStore(Long storeId) {
        List<PromotionInfo> promotions = promotionInfoRepository.findByStoreStoreIdOrderByDateDesc(storeId);
        
        return promotions.stream()
                .map(this::convertToListResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * PromotionInfo를 PromotionListResponseDto로 변환
     * @param promotion PromotionInfo 엔티티
     * @return PromotionListResponseDto
     */
    private PromotionListResponseDto convertToListResponseDto(PromotionInfo promotion) {
        return PromotionListResponseDto.builder()
                .id(promotion.getId())
                .title(promotion.getTitle())
                .image(promotion.getImage())
                .date(promotion.getDate())
                .likeCount(promotion.getLikeCount())
                .storeName(promotion.getStore().getStoreName())
                .storeLocation(promotion.getStore().getLocation())
                .build();
    }
    
    /**
     * PromotionInfo를 PromotionDetailResponseDto로 변환
     * @param promotion PromotionInfo 엔티티
     * @return PromotionDetailResponseDto
     */
    private PromotionDetailResponseDto convertToDetailResponseDto(PromotionInfo promotion) {
        Store store = promotion.getStore();
        
        return PromotionDetailResponseDto.builder()
                .id(promotion.getId())
                .title(promotion.getTitle())
                .image(promotion.getImage())
                .description(promotion.getDescription())
                .date(promotion.getDate())
                .likeCount(promotion.getLikeCount())
                .comment(promotion.getComment())
                .prompt(promotion.getPrompt())
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeLocation(store.getLocation())
                .storeImage(store.getStoreImage())
                .storeInformation(store.getInformation())
                .build();
    }
}

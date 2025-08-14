package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.dto.promotion.PromotionCreateRequestDto;
import com.hackthon.blog_generator_backend.dto.promotion.PromotionDetailResponseDto;
import com.hackthon.blog_generator_backend.dto.promotion.PromotionListResponseDto;
import com.hackthon.blog_generator_backend.service.PromotionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트엔드 연동을 위한 CORS 설정
public class PromotionInfoController {
    
    private final PromotionInfoService promotionInfoService;
    
    /**
     * 전체 프로모션 목록 조회 (Community 메인 페이지)
     * @return PromotionListResponseDto 리스트
     */
    @GetMapping("/promotions")
    public ResponseEntity<List<PromotionListResponseDto>> getAllPromotions() {
        try {
            List<PromotionListResponseDto> promotions = promotionInfoService.getAllPromotions();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 특정 프로모션 상세 정보 조회
     * @param promotionId 프로모션 ID
     * @return PromotionDetailResponseDto
     */
    @GetMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionDetailResponseDto> getPromotionDetail(@PathVariable Long promotionId) {
        try {
            PromotionDetailResponseDto promotion = promotionInfoService.getPromotionDetail(promotionId);
            return ResponseEntity.ok(promotion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 새 프로모션 등록
     * @param request 프로모션 등록 요청
     * @return PromotionDetailResponseDto
     */
    @PostMapping("/promotions")
    public ResponseEntity<PromotionDetailResponseDto> createPromotion(@RequestBody PromotionCreateRequestDto request) {
        try {
            PromotionDetailResponseDto promotion = promotionInfoService.createPromotion(request);
            return ResponseEntity.ok(promotion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 프로모션 좋아요 수 증가
     * @param promotionId 프로모션 ID
     * @return 업데이트된 PromotionDetailResponseDto
     */
    @PostMapping("/promotions/{promotionId}/like")
    public ResponseEntity<PromotionDetailResponseDto> likePromotion(@PathVariable Long promotionId) {
        try {
            PromotionDetailResponseDto promotion = promotionInfoService.increaseLikeCount(promotionId);
            return ResponseEntity.ok(promotion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 특정 가게의 프로모션 목록 조회
     * @param storeId 가게 ID
     * @return PromotionListResponseDto 리스트
     */
    @GetMapping("/stores/{storeId}/promotions")
    public ResponseEntity<List<PromotionListResponseDto>> getPromotionsByStore(@PathVariable Long storeId) {
        try {
            List<PromotionListResponseDto> promotions = promotionInfoService.getPromotionsByStore(storeId);
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

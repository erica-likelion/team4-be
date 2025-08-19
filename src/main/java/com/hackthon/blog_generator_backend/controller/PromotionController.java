package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.entity.PromotionInfo;
import com.hackthon.blog_generator_backend.repository.PromotionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard/promotions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PromotionController {
    
    private final PromotionInfoRepository promotionInfoRepository;
    
    // 모든 프로모션 조회 (Dashboard용)
    @GetMapping
    public ResponseEntity<List<PromotionInfo>> getAllPromotions() {
        try {
            List<PromotionInfo> promotions = promotionInfoRepository.findAll();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 특정 프로모션 조회
    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionInfo> getPromotionById(@PathVariable Long promotionId) {
        try {
            PromotionInfo promotion = promotionInfoRepository.findById(promotionId)
                    .orElse(null);
            if (promotion != null) {
                return ResponseEntity.ok(promotion);
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 제목으로 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<PromotionInfo>> searchPromotionsByTitle(@RequestParam String title) {
        try {
            List<PromotionInfo> promotions = promotionInfoRepository.findByTitleContaining(title);
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 특정 매장의 프로모션 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<PromotionInfo>> getPromotionsByStore(@PathVariable Long storeId) {
        try {
            List<PromotionInfo> promotions = promotionInfoRepository.findByStoreStoreId(storeId);
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 좋아요 수로 정렬하여 조회
    @GetMapping("/top-liked")
    public ResponseEntity<List<PromotionInfo>> getTopLikedPromotions() {
        try {
            List<PromotionInfo> promotions = promotionInfoRepository.findAllByOrderByLikeCountDesc();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

package com.hackthon.blog_generator_backend.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDetailResponseDto {
    
    private Long id;
    private String title;
    private String image;
    private String description;
    private LocalDate date;
    private Integer likeCount;
    private String comment;
    private String prompt; // AI 생성 시 사용된 프롬프트
    
    // 연관된 가게 정보 (상세)
    private Long storeId;
    private String storeName;
    private String storeLocation;
    private String storeImage;
    private String storeInformation;
}

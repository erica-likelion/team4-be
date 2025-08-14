package com.hackthon.blog_generator_backend.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionCreateRequestDto {
    
    private String title;
    private String image;
    private String description;
    private String comment;
    private String prompt; // AI 생성 시 사용된 프롬프트
    
    // 연관될 가게 정보
    private Long storeId;
}

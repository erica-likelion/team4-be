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
public class PromotionListResponseDto {
    
    private Long id;
    private String title;
    private String image;
    private LocalDate date;
    private Integer likeCount;
    
    // 연관된 가게 정보 (간단)
    private String storeName;
    private String storeLocation;
}

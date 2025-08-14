package com.hackthon.blog_generator_backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponseDto {
    
    private Long storeId;
    private String storeName;
    private String storeImage;
    private String information;
    private String location;
    private Integer storeTime;
    private String closedDays;
    private Boolean reservation;
    private String menu;
    private Integer count;
    
    // 편의시설 정보 포함
    private ConvenienceDto convenience;
}

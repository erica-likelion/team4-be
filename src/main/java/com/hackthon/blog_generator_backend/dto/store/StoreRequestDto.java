package com.hackthon.blog_generator_backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRequestDto {
    
    private String storeName;
    private String storeImage;
    private String information;
    private String location;
    private Integer storeTime;
    private String closedDays;
    private Boolean reservation;
    private String menu;
    
    // 편의시설 정보 (UI 폼에서 함께 입력)
    private Boolean wifi;
    private Boolean outlet;
    private Boolean pet;
    private Boolean packagingDelivery;
}

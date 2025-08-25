package com.hackthon.blog_generator_backend.dto.store;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRequestDto {
    
    @NotBlank(message = "매장명은 필수입니다")
    @Size(min = 2, max = 100, message = "매장명은 2자 이상 100자 이하여야 합니다")
    private String storeName;
    
    @Size(max = 500, message = "매장 이미지 URL은 500자 이하여야 합니다")
    private String storeImage;
    
    @Size(max = 1000, message = "매장 정보는 1000자 이하여야 합니다")
    private String information;
    
    @NotBlank(message = "위치는 필수입니다")
    @Size(max = 200, message = "위치는 200자 이하여야 합니다")
    private String location;
    
    @Min(value = 0, message = "오픈시간은 0 이상이어야 합니다")
    @Max(value = 23, message = "오픈시간은 23 이하여야 합니다")
    private Integer openTime;
    
    @Min(value = 0, message = "마감시간은 0 이상이어야 합니다")
    @Max(value = 23, message = "마감시간은 23 이하여야 합니다")
    private Integer closeTime;
    
    @Size(max = 100, message = "휴무일 정보는 100자 이하여야 합니다")
    private String closedDays;
    
    private Boolean reservation;
    
    @Size(max = 2000, message = "메뉴 정보는 2000자 이하여야 합니다")
    private String menu;
    
    @Size(max = 50, message = "업종은 50자 이하여야 합니다")
    private String businessType;
    
    // 편의시설 정보 (사용자 입력 받음)
    private Boolean wifi;           // WiFi 제공 여부
    private Boolean outlet;         // 콘센트 제공 여부
    private Boolean pet;            // 반려동물 동반 가능 여부
    private Boolean packagingDelivery; // 포장/배달 서비스 여부
}

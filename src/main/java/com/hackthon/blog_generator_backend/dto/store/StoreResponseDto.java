package com.hackthon.blog_generator_backend.dto.store;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.entity.Convenience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String businessType;
    
    // 편의시설 정보 포함
    private ConvenienceDto convenience;
    
    // 편의시설 정보 변환 메서드
    public static StoreResponseDto fromEntity(Store store) {
        ConvenienceDto convenienceDto = null;
        
        // 편의시설 정보가 있으면 변환
        if (store.getConvenienceList() != null && !store.getConvenienceList().isEmpty()) {
            Convenience convenience = store.getConvenienceList().get(0); // 첫 번째 편의시설 정보 사용
            convenienceDto = ConvenienceDto.builder()
                    .wifi(convenience.getWifi())
                    .outlet(convenience.getOutlet())
                    .pet(convenience.getPet())
                    .packagingDelivery(convenience.getPackagingDelivery())
                    .build();
            
            System.out.println("편의시설 정보 변환 완료: " + convenienceDto);
        } else {
            System.out.println("편의시설 정보 없음 - Store ID: " + store.getStoreId() + 
                             ", ConvenienceList: " + store.getConvenienceList());
        }
        
        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeImage(store.getStoreImage())
                .information(store.getInformation())
                .location(store.getLocation())
                .storeTime(store.getStoreTime())
                .closedDays(store.getClosedDays())
                .reservation(store.getReservation())
                .menu(store.getMenu())
                .count(store.getCount())
                .businessType(store.getBusinessType())
                .convenience(convenienceDto)
                .build();
    }
}

package com.hackthon.blog_generator_backend.dto.store;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.entity.Convenience;
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
    private Integer openTime;
    private Integer closeTime;
    private String closedDays;
    private Boolean reservation;
    private String menu;
    private Integer count;
    private String businessType;
    
    // 편의시설 정보 개별 필드로 노출
    private Boolean wifi;
    private Boolean outlet;  
    private Boolean pet;
    private Boolean packagingDelivery;
    
    // 편의시설 정보 변환 메서드
    public static StoreResponseDto fromEntity(Store store) {
        // 편의시설 정보 기본값 설정
        Boolean wifi = false;
        Boolean outlet = false;
        Boolean pet = false;
        Boolean packagingDelivery = false;
        
        // 편의시설 정보가 있으면 개별 필드로 설정
        if (store.getConvenienceList() != null && !store.getConvenienceList().isEmpty()) {
            Convenience convenience = store.getConvenienceList().get(0); // 첫 번째 편의시설 정보 사용
            wifi = convenience.getWifi() != null ? convenience.getWifi() : false;
            outlet = convenience.getOutlet() != null ? convenience.getOutlet() : false;
            pet = convenience.getPet() != null ? convenience.getPet() : false;
            packagingDelivery = convenience.getPackagingDelivery() != null ? convenience.getPackagingDelivery() : false;
            
            System.out.println("편의시설 정보 변환 완료 - wifi: " + wifi + ", outlet: " + outlet + ", pet: " + pet + ", packagingDelivery: " + packagingDelivery);
        } else {
            System.out.println("편의시설 정보 없음 - Store ID: " + store.getStoreId() + ", 기본값 false로 설정");
        }
        
        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeImage(store.getStoreImage())
                .information(store.getInformation())
                .location(store.getLocation())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .closedDays(store.getClosedDays())
                .reservation(store.getReservation())
                .menu(store.getMenu())
                .count(store.getCount() != null ? store.getCount() : 0)  // count null이면 기본값 0
                .businessType(store.getBusinessType())
                .wifi(wifi)
                .outlet(outlet)
                .pet(pet)
                .packagingDelivery(packagingDelivery)
                .build();
    }
}

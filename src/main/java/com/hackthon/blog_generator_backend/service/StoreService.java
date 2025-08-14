package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.dto.store.ConvenienceDto;
import com.hackthon.blog_generator_backend.dto.store.StoreRequestDto;
import com.hackthon.blog_generator_backend.dto.store.StoreResponseDto;
import com.hackthon.blog_generator_backend.entity.Convenience;
import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.repository.ConvenienceRepository;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final ConvenienceRepository convenienceRepository;
    
    /**
     * 가게 정보 조회 (편의시설 정보 포함)
     * @param storeId 가게 ID
     * @return StoreResponseDto
     */
    public StoreResponseDto getStore(Long storeId) {
        // Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다. ID: " + storeId));
        
        // 연관된 Convenience 조회
        Optional<Convenience> convenience = convenienceRepository.findByStoreStoreId(storeId);
        
        // DTO로 변환
        return convertToResponseDto(store, convenience.orElse(null));
    }
    
    /**
     * 가게 정보 등록 (편의시설 정보 함께)
     * @param request 가게 정보 요청 DTO
     * @return StoreResponseDto
     */
    @Transactional
    public StoreResponseDto createStore(StoreRequestDto request) {
        // Store 엔티티 생성 및 저장
        Store store = Store.builder()
                .storeName(request.getStoreName())
                .storeImage(request.getStoreImage())
                .information(request.getInformation())
                .location(request.getLocation())
                .storeTime(request.getStoreTime())
                .closedDays(request.getClosedDays())
                .reservation(request.getReservation())
                .menu(request.getMenu())
                .count(0) // 초기값
                .build();
        
        Store savedStore = storeRepository.save(store);
        
        // Convenience 엔티티 생성 및 저장
        Convenience convenience = Convenience.builder()
                .wifi(request.getWifi())
                .outlet(request.getOutlet())
                .pet(request.getPet())
                .packagingDelivery(request.getPackagingDelivery())
                .store(savedStore)
                .build();
        
        Convenience savedConvenience = convenienceRepository.save(convenience);
        
        // DTO로 변환 후 반환
        return convertToResponseDto(savedStore, savedConvenience);
    }
    
    /**
     * 가게 정보 수정
     * @param storeId 가게 ID
     * @param request 수정할 가게 정보
     * @return StoreResponseDto
     */
    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto request) {
        // 기존 Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다. ID: " + storeId));
        
        // Store 정보 업데이트
        store.setStoreName(request.getStoreName());
        store.setStoreImage(request.getStoreImage());
        store.setInformation(request.getInformation());
        store.setLocation(request.getLocation());
        store.setStoreTime(request.getStoreTime());
        store.setClosedDays(request.getClosedDays());
        store.setReservation(request.getReservation());
        store.setMenu(request.getMenu());
        
        // Convenience 정보 업데이트
        Optional<Convenience> existingConvenience = convenienceRepository.findByStoreStoreId(storeId);
        Convenience convenience;
        
        if (existingConvenience.isPresent()) {
            // 기존 편의시설 정보 업데이트
            convenience = existingConvenience.get();
            convenience.setWifi(request.getWifi());
            convenience.setOutlet(request.getOutlet());
            convenience.setPet(request.getPet());
            convenience.setPackagingDelivery(request.getPackagingDelivery());
        } else {
            // 새로운 편의시설 정보 생성
            convenience = Convenience.builder()
                    .wifi(request.getWifi())
                    .outlet(request.getOutlet())
                    .pet(request.getPet())
                    .packagingDelivery(request.getPackagingDelivery())
                    .store(store)
                    .build();
        }
        
        Convenience savedConvenience = convenienceRepository.save(convenience);
        
        // 4. DTO로 변환 후 반환
        return convertToResponseDto(store, savedConvenience);
    }
    
    /**
     * Store와 Convenience 엔티티를 StoreResponseDto로 변환
     * @param store Store 엔티티
     * @param convenience Convenience 엔티티
     * @return StoreResponseDto
     */
    private StoreResponseDto convertToResponseDto(Store store, Convenience convenience) {
        ConvenienceDto convenienceDto = null;
        
        if (convenience != null) {
            convenienceDto = ConvenienceDto.builder()
                    .wifi(convenience.getWifi())
                    .outlet(convenience.getOutlet())
                    .pet(convenience.getPet())
                    .packagingDelivery(convenience.getPackagingDelivery())
                    .build();
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
                .convenience(convenienceDto)
                .build();
    }
}

package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.entity.Convenience;
import com.hackthon.blog_generator_backend.dto.store.StoreRequestDto;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
import com.hackthon.blog_generator_backend.repository.ConvenienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final ConvenienceRepository convenienceRepository;
    
    // 모든 매장 조회 (Dashboard용) - 편의시설 정보 포함
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAllWithConvenience();
        
        // 편의시설 정보 조회 상태 로깅
        for (Store store : stores) {
            System.out.println("Store ID: " + store.getStoreId() + 
                             ", ConvenienceList size: " + 
                             (store.getConvenienceList() != null ? store.getConvenienceList().size() : "null"));
        }
        
        return stores;
    }
    
    // 매장 ID로 조회 - 편의시설 정보 포함
    public Store getStoreById(Long storeId) {
        return storeRepository.findByIdWithConvenience(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }
    
    // 매장명으로 검색
    public List<Store> searchStoresByName(String storeName) {
        return storeRepository.findByStoreNameContaining(storeName);
    }
    
    // 위치로 검색
    public List<Store> searchStoresByLocation(String location) {
        return storeRepository.findByLocationContaining(location);
    }
    
    // 예약 가능한 매장 조회
    public List<Store> getReservableStores() {
        return storeRepository.findByReservationTrue();
    }
    
    // 매장 정보로 검색
    public List<Store> searchStoresByInformation(String information) {
        return storeRepository.findByInformationContaining(information);
    }
    
    // 가게 등록 (편의시설 정보 고정값으로 설정)
    @Transactional
    public Store createStore(StoreRequestDto requestDto) {
        System.out.println("=== Store 생성 시작 ===");
        System.out.println("요청 데이터: " + requestDto);
        
        // Store 엔티티 생성
        Store store = Store.builder()
                .storeName(requestDto.getStoreName())
                .storeImage(requestDto.getStoreImage())
                .information(requestDto.getInformation())
                .location(requestDto.getLocation())
                .storeTime(requestDto.getStoreTime())
                .closedDays(requestDto.getClosedDays())
                .reservation(requestDto.getReservation())
                .menu(requestDto.getMenu())
                .build();
        
        // Store 저장
        Store savedStore = storeRepository.save(store);
        System.out.println("Store 저장 완료 - ID: " + savedStore.getStoreId());
        
        // 편의시설 정보를 고정값으로 설정하여 저장
        System.out.println("편의시설 정보 고정값으로 저장 시작");
        
        Convenience convenience = Convenience.builder()
                .wifi(true)           // 고정값: WiFi 제공
                .outlet(true)         // 고정값: 콘센트 제공
                .pet(false)           // 고정값: 반려동물 동반 불가
                .packagingDelivery(true) // 고정값: 포장/배달 가능
                .store(savedStore)
                .build();
        
        Convenience savedConvenience = convenienceRepository.save(convenience);
        System.out.println("편의시설 정보 저장 완료 - ID: " + savedConvenience.getId());
        
        // 편의시설 정보를 포함하여 Store 재조회
        return storeRepository.findByIdWithConvenience(savedStore.getStoreId())
                .orElse(savedStore);
    }
    
    // 가게 정보 수정
    @Transactional
    public Store updateStore(Store store) {
        if (!storeRepository.existsById(store.getStoreId())) {
            throw new RuntimeException("Store not found with id: " + store.getStoreId());
        }
        return storeRepository.save(store);
    }
    
    // 가게 삭제
    @Transactional
    public void deleteStore(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store not found with id: " + storeId);
        }
        storeRepository.deleteById(storeId);
    }
}

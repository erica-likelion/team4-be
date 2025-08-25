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
    
    // 모든 매장 조회 (Dashboard용)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
    
    // 매장 ID로 조회
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
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
    
    // 가게 등록 (편의시설 정보 포함)
    @Transactional
    public Store createStore(StoreRequestDto requestDto) {
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
        
        // Convenience 엔티티 생성 및 저장
        if (requestDto.getWifi() != null || requestDto.getOutlet() != null || 
            requestDto.getPet() != null || requestDto.getPackagingDelivery() != null) {
            
            Convenience convenience = Convenience.builder()
                    .wifi(requestDto.getWifi())
                    .outlet(requestDto.getOutlet())
                    .pet(requestDto.getPet())
                    .packagingDelivery(requestDto.getPackagingDelivery())
                    .store(savedStore)
                    .build();
            
            convenienceRepository.save(convenience);
        }
        
        return savedStore;
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

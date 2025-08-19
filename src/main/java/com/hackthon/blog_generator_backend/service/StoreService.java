package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
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
}

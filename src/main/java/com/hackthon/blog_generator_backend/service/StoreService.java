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
    
    // 가게 등록
    @Transactional
    public Store createStore(Store store) {
        return storeRepository.save(store);
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

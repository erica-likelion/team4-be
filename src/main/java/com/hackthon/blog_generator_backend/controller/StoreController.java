package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/stores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreController {
    
    private final StoreService storeService;
    
    // 모든 매장 조회 (Dashboard용)
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        try {
            List<Store> stores = storeService.getAllStores();
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 특정 매장 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        try {
            Store store = storeService.getStoreById(storeId);
            return ResponseEntity.ok(store);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
    
    // 매장명으로 검색
    @GetMapping("/search/name")
    public ResponseEntity<List<Store>> searchStoresByName(@RequestParam String storeName) {
        try {
            List<Store> stores = storeService.searchStoresByName(storeName);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 위치로 검색
    @GetMapping("/search/location")
    public ResponseEntity<List<Store>> searchStoresByLocation(@RequestParam String location) {
        try {
            List<Store> stores = storeService.searchStoresByLocation(location);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 예약 가능한 매장 조회
    @GetMapping("/reservable")
    public ResponseEntity<List<Store>> getReservableStores() {
        try {
            List<Store> stores = storeService.getReservableStores();
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

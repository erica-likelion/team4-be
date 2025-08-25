package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.dto.store.StoreRequestDto;
import com.hackthon.blog_generator_backend.dto.store.StoreResponseDto;
import com.hackthon.blog_generator_backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard/stores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreController {
    
    private final StoreService storeService;
    
    // 모든 매장 조회 (Dashboard용)
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getAllStores() {
        try {
            List<Store> stores = storeService.getAllStores();
            List<StoreResponseDto> responseDtos = stores.stream()
                    .map(StoreResponseDto::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 특정 매장 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStoreById(@PathVariable Long storeId) {
        try {
            Store store = storeService.getStoreById(storeId);
            StoreResponseDto responseDto = StoreResponseDto.fromEntity(store);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
    
    // 매장명으로 검색
    @GetMapping("/search/name")
    public ResponseEntity<List<StoreResponseDto>> searchStoresByName(@RequestParam String storeName) {
        try {
            List<Store> stores = storeService.searchStoresByName(storeName);
            List<StoreResponseDto> responseDtos = stores.stream()
                    .map(StoreResponseDto::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 위치로 검색
    @GetMapping("/search/location")
    public ResponseEntity<List<StoreResponseDto>> searchStoresByLocation(@RequestParam String location) {
        try {
            List<Store> stores = storeService.searchStoresByLocation(location);
            List<StoreResponseDto> responseDtos = stores.stream()
                    .map(StoreResponseDto::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 예약 가능한 매장 조회
    @GetMapping("/reservable")
    public ResponseEntity<List<StoreResponseDto>> getReservableStores() {
        try {
            List<Store> stores = storeService.getReservableStores();
            List<StoreResponseDto> responseDtos = stores.stream()
                    .map(StoreResponseDto::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 가게 등록 (POST)
    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto requestDto) {
        try {
            Store createdStore = storeService.createStore(requestDto);
            StoreResponseDto responseDto = StoreResponseDto.fromEntity(createdStore);
            return ResponseEntity.status(201).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // 가게 정보 수정 (PUT)
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long storeId, @RequestBody Store store) {
        try {
            store.setStoreId(storeId);
            Store updatedStore = storeService.updateStore(store);
            StoreResponseDto responseDto = StoreResponseDto.fromEntity(updatedStore);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

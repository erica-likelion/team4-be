package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.dto.store.StoreRequestDto;
import com.hackthon.blog_generator_backend.dto.store.StoreResponseDto;
import com.hackthon.blog_generator_backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트엔드 연동을 위한 CORS 설정
public class StoreController {
    
    private final StoreService storeService;
    
    /**
     * 가게 정보 조회 (편의시설 정보 포함)
     * @param storeId 가게 ID
     * @return StoreResponseDto
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable Long storeId) {
        try {
            StoreResponseDto response = storeService.getStore(storeId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 가게 정보 등록 (편의시설 정보 함께)
     * @param request 가게 정보 등록 요청
     * @return StoreResponseDto
     */
    @PostMapping("/store")
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto request) {
        try {
            StoreResponseDto response = storeService.createStore(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 가게 정보 수정
     * @param storeId 가게 ID
     * @param request 수정할 가게 정보
     * @return StoreResponseDto
     */
    @PutMapping("/store/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(
            @PathVariable Long storeId,
            @RequestBody StoreRequestDto request) {
        try {
            StoreResponseDto response = storeService.updateStore(storeId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.dto.MadeRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeResponseDto;
import com.hackthon.blog_generator_backend.dto.UserInputRequestDto;
import com.hackthon.blog_generator_backend.service.MadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/made")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MadeController {
    
    private final MadeService madeService;
    
    // Made 생성 (POST)
    @PostMapping
    public ResponseEntity<MadeResponseDto> createMade(@RequestBody MadeRequestDto requestDto) {
        try {
            MadeResponseDto response = madeService.createMade(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 결과 조회 (GET)
    @GetMapping("/result/{madeId}")
    public ResponseEntity<MadeResponseDto> getResult(@PathVariable Long madeId) {
        try {
            MadeResponseDto response = madeService.getResult(madeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    // 사용자 입력 처리 (POST)
    @PostMapping("/user-input")
    public ResponseEntity<MadeResponseDto> processUserInput(@RequestBody UserInputRequestDto requestDto) {
        try {
            MadeResponseDto response = madeService.processUserInput(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 모든 Made 목록 조회 (GET)
    @GetMapping
    public ResponseEntity<List<MadeResponseDto>> getAllMade() {
        try {
            List<MadeResponseDto> response = madeService.getAllMade();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

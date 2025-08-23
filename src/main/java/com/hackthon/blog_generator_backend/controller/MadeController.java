package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.dto.MadeRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeResponseDto;
import com.hackthon.blog_generator_backend.dto.UserInputRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeImageRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeModifyRequestDto;
import com.hackthon.blog_generator_backend.service.MadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/made")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MadeController {
    
    private final MadeService madeService;
    
    // Made 생성 (POST)
    @PostMapping
    public ResponseEntity<MadeResponseDto> createMade(@Valid @RequestBody MadeRequestDto requestDto) {
        MadeResponseDto response = madeService.createMade(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // 사진 업로드 및 블로그 생성 (POST) - JSON 요청 지원
    @PostMapping("/upload-images")
    public ResponseEntity<MadeResponseDto> createMadeWithImages(@Valid @RequestBody MadeImageRequestDto requestDto) {
        MadeResponseDto response = madeService.createMadeWithImages(
            requestDto.getStoreId(), 
            requestDto.getUserInput(), 
            requestDto.getHashTags(), 
            requestDto.getDetailedRequest(), 
            null // 이미지는 현재 null로 처리
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // 블로그 내용 수정 (POST) - JSON 요청 지원
    @PostMapping("/modify-content")
    public ResponseEntity<MadeResponseDto> modifyBlogContent(@Valid @RequestBody MadeModifyRequestDto requestDto) {
        MadeResponseDto response = madeService.modifyBlogContent(requestDto.getMadeId(), requestDto.getModificationRequest());
        return ResponseEntity.ok(response);
    }
    
    // 결과 조회 (GET)
    @GetMapping("/result/{madeId}")
    public ResponseEntity<MadeResponseDto> getResult(@PathVariable Long madeId) {
        MadeResponseDto response = madeService.getResult(madeId);
        return ResponseEntity.ok(response);
    }
    
    // 사용자 입력 처리 (POST)
    @PostMapping("/user-input")
    public ResponseEntity<MadeResponseDto> processUserInput(@Valid @RequestBody UserInputRequestDto requestDto) {
        MadeResponseDto response = madeService.processUserInput(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // 모든 Made 목록 조회 (GET)
    @GetMapping
    public ResponseEntity<List<MadeResponseDto>> getAllMade() {
        List<MadeResponseDto> response = madeService.getAllMade();
        return ResponseEntity.ok(response);
    }
}

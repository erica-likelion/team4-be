package com.hackthon.blog_generator_backend.controller;

import com.hackthon.blog_generator_backend.dto.PromptCopyDto;
import com.hackthon.blog_generator_backend.dto.PromptDto;
import com.hackthon.blog_generator_backend.service.PromptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PromptController {

    private final PromptService promptService;

    /**
     * 인기 프롬프트 조회
     */
    @GetMapping("/popular")
    public ResponseEntity<List<PromptDto>> getPopularPrompts() {
        log.info("인기 프롬프트 조회 요청");
        List<PromptDto> prompts = promptService.getPopularPrompts();
        return ResponseEntity.ok(prompts);
    }

    /**
     * 최근 생성된 프롬프트 조회
     */
    @GetMapping("/recent")
    public ResponseEntity<List<PromptDto>> getRecentPrompts() {
        log.info("최근 프롬프트 조회 요청");
        List<PromptDto> prompts = promptService.getRecentPrompts();
        return ResponseEntity.ok(prompts);
    }

    /**
     * 프롬프트 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<PromptDto>> searchPrompts(@RequestParam String keyword) {
        log.info("프롬프트 검색 요청: keyword={}", keyword);
        List<PromptDto> prompts = promptService.searchPrompts(keyword);
        return ResponseEntity.ok(prompts);
    }

    /**
     * 특정 매장의 프롬프트 조회
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<PromptDto>> getPromptsByStore(@PathVariable Long storeId) {
        log.info("매장별 프롬프트 조회 요청: storeId={}", storeId);
        List<PromptDto> prompts = promptService.getPromptsByStore(storeId);
        return ResponseEntity.ok(prompts);
    }

    /**
     * 프롬프트 재사용 (복사)
     */
    @GetMapping("/reuse/{madeId}")
    public ResponseEntity<PromptCopyDto> getPromptForReuse(@PathVariable Long madeId) {
        log.info("프롬프트 재사용 요청: madeId={}", madeId);
        PromptDto promptDto = promptService.getPromptForReuse(madeId);
        
        PromptCopyDto copyDto = PromptCopyDto.builder()
                .madeId(promptDto.getMadeId())
                .prompt(promptDto.getPrompt())
                .userInput(promptDto.getUserInput())
                .resultTitle(promptDto.getResultTitle())
                .resultContent(promptDto.getResultContent())
                .hashTag(promptDto.getHashTag())
                .storeId(promptDto.getStoreId())
                .storeName(promptDto.getStoreName())
                .copyMessage("프롬프트가 성공적으로 복사되었습니다!")
                .build();
        
        return ResponseEntity.ok(copyDto);
    }

    /**
     * 프롬프트 통계 조회
     */
    @GetMapping("/stats")
    public ResponseEntity<List<Object[]>> getPromptStats() {
        log.info("프롬프트 통계 조회 요청");
        List<Object[]> stats = promptService.getPromptStats();
        return ResponseEntity.ok(stats);
    }
}

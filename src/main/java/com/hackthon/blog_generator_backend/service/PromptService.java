package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.dto.PromptDto;
import com.hackthon.blog_generator_backend.entity.Made;
import com.hackthon.blog_generator_backend.repository.MadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PromptService {

    private final MadeRepository madeRepository;

    /**
     * 인기 프롬프트 조회 (사용 빈도 기준)
     */
    public List<PromptDto> getPopularPrompts() {
        List<Object[]> stats = madeRepository.findPromptUsageStats();
        
        return stats.stream()
                .limit(10)
                .map(stat -> {
                    String prompt = (String) stat[0];
                    Long usageCount = (Long) stat[1];
                    
                    // 해당 프롬프트를 사용한 첫 번째 Made 찾기
                    List<Made> mades = madeRepository.findByPromptContaining(prompt);
                    if (!mades.isEmpty()) {
                        Made made = mades.get(0);
                        return convertToPromptDto(made, usageCount.intValue(), calculatePopularityScore(usageCount));
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    /**
     * 최근 생성된 프롬프트 조회
     */
    public List<PromptDto> getRecentPrompts() {
        return madeRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .filter(made -> made.getPrompt() != null && !made.getPrompt().trim().isEmpty())
                .map(made -> convertToPromptDto(made, 1, 1.0))
                .collect(Collectors.toList());
    }

    /**
     * 프롬프트 검색
     */
    public List<PromptDto> searchPrompts(String keyword) {
        return madeRepository.findByPromptContaining(keyword)
                .stream()
                .filter(made -> made.getPrompt() != null && !made.getPrompt().trim().isEmpty())
                .map(made -> convertToPromptDto(made, 1, 1.0))
                .collect(Collectors.toList());
    }

    /**
     * 특정 매장의 프롬프트 조회
     */
    public List<PromptDto> getPromptsByStore(Long storeId) {
        return madeRepository.findByStoreStoreId(storeId)
                .stream()
                .filter(made -> made.getPrompt() != null && !made.getPrompt().trim().isEmpty())
                .map(made -> convertToPromptDto(made, 1, 1.0))
                .collect(Collectors.toList());
    }

    /**
     * 프롬프트 재사용 (복사)
     */
    public PromptDto getPromptForReuse(Long madeId) {
        Made made = madeRepository.findById(madeId)
                .orElseThrow(() -> new RuntimeException("Made not found with id: " + madeId));
        
        if (made.getPrompt() == null || made.getPrompt().trim().isEmpty()) {
            throw new RuntimeException("No prompt available for reuse");
        }
        
        return convertToPromptDto(made, 1, 1.0);
    }

    /**
     * 프롬프트 통계 조회
     */
    public List<Object[]> getPromptStats() {
        return madeRepository.findPromptUsageStats();
    }

    /**
     * Made 엔티티를 PromptDto로 변환
     */
    private PromptDto convertToPromptDto(Made made, Integer usageCount, Double popularityScore) {
        return PromptDto.builder()
                .madeId(made.getMadeId())
                .prompt(made.getPrompt())
                .userInput(made.getUserInput())
                .resultTitle(made.getResultTitle())
                .resultContent(made.getResultContent())
                .hashTag(made.getHashTag())
                .storeId(made.getStore() != null ? made.getStore().getStoreId() : null)
                .storeName(made.getStore() != null ? made.getStore().getStoreName() : null)
                .createdAt(made.getCreatedAt())
                .usageCount(usageCount)
                .popularityScore(popularityScore)
                .build();
    }

    /**
     * 인기도 점수 계산 (간단한 알고리즘)
     */
    private Double calculatePopularityScore(Long usageCount) {
        if (usageCount == null || usageCount == 0) return 1.0;
        return Math.min(usageCount * 0.5, 10.0); // 최대 10점
    }
}

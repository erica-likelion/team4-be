package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.Made;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MadeRepository extends JpaRepository<Made, Long> {
    
    // 사용자 입력으로 Made 조회
    List<Made> findByUserInputContaining(String userInput);
    
    // 해시태그로 Made 조회
    List<Made> findByHashTagContaining(String hashTag);
    
    // 특정 매장의 Made 목록 조회
    List<Made> findByStoreStoreId(Long storeId);
    
    // 결과 제목으로 Made 조회
    Optional<Made> findByResultTitle(String resultTitle);
    
                    // 프롬프트로 Made 조회
                List<Made> findByPromptContaining(String prompt);

                // 최근 생성된 Made 조회 (프롬프트 공유용)
                List<Made> findTop10ByOrderByCreatedAtDesc();

                // 프롬프트 사용 통계 조회
                @Query("SELECT m.prompt, COUNT(m) as usageCount FROM Made m WHERE m.prompt IS NOT NULL GROUP BY m.prompt ORDER BY usageCount DESC")
                List<Object[]> findPromptUsageStats();
}

package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.PromotionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionInfoRepository extends JpaRepository<PromotionInfo, Long> {
    
    /**
     * 모든 프로모션을 최신순으로 조회
     * @return PromotionInfo 리스트 (날짜 내림차순)
     */
    List<PromotionInfo> findAllByOrderByDateDesc();
    
    /**
     * 특정 가게의 프로모션 목록 조회
     * @param storeId 가게 ID
     * @return PromotionInfo 리스트
     */
    List<PromotionInfo> findByStoreStoreIdOrderByDateDesc(Long storeId);
    
    /**
     * 좋아요 수가 많은 프로모션 조회 (인기순)
     * @return PromotionInfo 리스트 (좋아요 수 내림차순)
     */
    List<PromotionInfo> findAllByOrderByLikeCountDesc();
    
    /**
     * Store 정보와 함께 프로모션 조회 (JOIN 쿼리 최적화)
     * @return PromotionInfo 리스트 (Store 정보 포함)
     */
    @Query("SELECT p FROM PromotionInfo p JOIN FETCH p.store ORDER BY p.date DESC")
    List<PromotionInfo> findAllWithStoreOrderByDateDesc();
}

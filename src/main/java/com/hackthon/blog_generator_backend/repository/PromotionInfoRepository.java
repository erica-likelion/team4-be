package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.PromotionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionInfoRepository extends JpaRepository<PromotionInfo, Long> {
    
    // 제목으로 검색
    List<PromotionInfo> findByTitleContaining(String title);
    
    // 특정 매장의 프로모션 조회
    List<PromotionInfo> findByStoreStoreId(Long storeId);
    
    // 날짜 범위로 프로모션 조회
    List<PromotionInfo> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // 좋아요 수로 정렬하여 조회
    List<PromotionInfo> findAllByOrderByLikeCountDesc();
    
    // 특정 날짜의 프로모션 조회
    List<PromotionInfo> findByDate(LocalDate date);
}

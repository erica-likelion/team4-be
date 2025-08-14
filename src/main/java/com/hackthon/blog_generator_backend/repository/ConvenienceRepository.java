package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.Convenience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConvenienceRepository extends JpaRepository<Convenience, Long> {
    
    /**
     * Store ID로 편의시설 정보 조회
     * @param storeId Store의 ID
     * @return Convenience 엔티티 (Optional)
     */
    Optional<Convenience> findByStoreStoreId(Long storeId);
    
    /**
     * Store ID로 편의시설 정보 삭제
     * @param storeId Store의 ID
     */
    void deleteByStoreStoreId(Long storeId);
}

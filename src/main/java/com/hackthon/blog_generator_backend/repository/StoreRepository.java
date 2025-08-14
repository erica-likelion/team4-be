package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    
    /**
     * 가게명으로 Store 조회
     * @param storeName 가게명
     * @return Store 엔티티 (Optional)
     */
    Optional<Store> findByStoreName(String storeName);
    
    /**
     * 지역명으로 Store 검색 (부분 일치)
     * @param location 지역명
     * @return Store 리스트
     */
    java.util.List<Store> findByLocationContaining(String location);
}

package com.hackthon.blog_generator_backend.repository;

import com.hackthon.blog_generator_backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    
    // 매장명으로 검색 (편의시설 정보 포함)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.storeName LIKE %:storeName%")
    List<Store> findByStoreNameContaining(@Param("storeName") String storeName);
    
    // 위치로 검색 (편의시설 정보 포함)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.location LIKE %:location%")
    List<Store> findByLocationContaining(@Param("location") String location);
    
    // 예약 가능한 매장 조회 (편의시설 정보 포함)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.reservation = true")
    List<Store> findByReservationTrue();
    
    // 특정 정보를 포함하는 매장 조회 (편의시설 정보 포함)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.information LIKE %:information%")
    List<Store> findByInformationContaining(@Param("information") String information);
    
    // 매장명으로 정확히 조회 (편의시설 정보 포함)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.storeName = :storeName")
    Optional<Store> findByStoreName(@Param("storeName") String storeName);
    
    // 편의시설 정보와 함께 모든 매장 조회 (JOIN FETCH)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList")
    List<Store> findAllWithConvenience();
    
    // 편의시설 정보와 함께 특정 매장 조회 (JOIN FETCH)
    @Query("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.convenienceList WHERE s.storeId = :storeId")
    Optional<Store> findByIdWithConvenience(Long storeId);
}

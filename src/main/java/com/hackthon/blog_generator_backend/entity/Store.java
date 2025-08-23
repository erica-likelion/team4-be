package com.hackthon.blog_generator_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_image")
    private String storeImage;

    @Column(name = "information")
    private String information;

    @Column(name = "location")
    private String location;

    @Column(name = "store_time")
    private Integer storeTime;

    @Column(name = "closed_days")
    private String closedDays;

    @Column(name = "reservation")
    private Boolean reservation;

    @Column(name = "menu")
    private String menu;

    @Column(name = "count")
    private Integer count;

    // 관계 매핑 - 무한 순환 참조 방지
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<Made> madeList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<Convenience> convenienceList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<PromotionInfo> promotionInfoList = new ArrayList<>();
}
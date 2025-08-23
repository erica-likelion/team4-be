package com.hackthon.blog_generator_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "convenience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Convenience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "outlet")
    private Boolean outlet;

    @Column(name = "pet")
    private Boolean pet;

    @Column(name = "packaging_delivery")
    private Boolean packagingDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private Store store;
}
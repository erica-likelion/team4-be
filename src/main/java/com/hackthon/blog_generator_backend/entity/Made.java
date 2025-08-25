package com.hackthon.blog_generator_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "made")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Made {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "made_id")
    private Long madeId;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "hash_tag")
    private String hashTag;

    @Column(name = "prompt", columnDefinition = "TEXT")
    private String prompt;  // 핵심: 사용된 프롬프트 저장

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "result_title")
    private String resultTitle;

    @Column(name = "result_content", columnDefinition = "TEXT")
    private String resultContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private Store store;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
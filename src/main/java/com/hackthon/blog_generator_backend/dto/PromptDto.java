package com.hackthon.blog_generator_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptDto {

    private Long madeId;
    private String prompt;
    private String userInput;
    private String resultTitle;
    private String resultContent;
    private String hashTag;
    private Long storeId;
    private String storeName;
    private LocalDateTime createdAt;
    private Integer usageCount;
    private Double popularityScore;
}

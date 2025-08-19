package com.hackthon.blog_generator_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MadeResponseDto {
    
    private Long madeId;
    private String image;
    private String hashTag;
    private String prompt;
    private String userInput;
    private String resultTitle;
    private String resultContent;
    private Long storeId;
    private String storeName;
}

package com.hackthon.blog_generator_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptCopyDto {

    private Long madeId;
    private String prompt;
    private String userInput;
    private String resultTitle;
    private String resultContent;
    private String hashTag;
    private Long storeId;
    private String storeName;
    private String copyMessage;
}

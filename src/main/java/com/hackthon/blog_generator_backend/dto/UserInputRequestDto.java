package com.hackthon.blog_generator_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputRequestDto {
    
    private String userInput;
    private Long storeId;
    private String prompt;
}

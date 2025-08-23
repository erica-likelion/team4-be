package com.hackthon.blog_generator_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MadeImageRequestDto {
    
    @NotNull(message = "매장 ID는 필수입니다")
    @Positive(message = "매장 ID는 양수여야 합니다")
    private Long storeId;
    
    @NotBlank(message = "사용자 입력은 필수입니다")
    @Size(min = 10, max = 1000, message = "사용자 입력은 10자 이상 1000자 이하여야 합니다")
    private String userInput;
    
    @Size(max = 200, message = "해시태그는 200자 이하여야 합니다")
    private String hashTags;
    
    @Size(max = 500, message = "상세 요청은 500자 이하여야 합니다")
    private String detailedRequest;
}






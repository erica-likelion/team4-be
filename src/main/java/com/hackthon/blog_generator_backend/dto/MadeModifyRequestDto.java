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
public class MadeModifyRequestDto {
    
    @NotNull(message = "Made ID는 필수입니다")
    @Positive(message = "Made ID는 양수여야 합니다")
    private Long madeId;
    
    @NotBlank(message = "수정 요청은 필수입니다")
    @Size(min = 10, max = 500, message = "수정 요청은 10자 이상 500자 이하여야 합니다")
    private String modificationRequest;
}






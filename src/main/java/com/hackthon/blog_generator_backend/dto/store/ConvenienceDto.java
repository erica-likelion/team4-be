package com.hackthon.blog_generator_backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvenienceDto {
    
    private Boolean wifi;
    private Boolean outlet;
    private Boolean pet;
    private Boolean packagingDelivery;
}

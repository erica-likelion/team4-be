package com.hackthon.blog_generator_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

                    @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOriginPatterns("*")  // 모든 도메인 허용 (개발 환경)
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                            .allowedHeaders("*")
                            .allowCredentials(false)  // true에서 false로 변경
                            .maxAge(3600); // 1시간 동안 preflight 요청 캐시
                }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 허용할 도메인들 (개발 환경)
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // 허용할 HTTP 메서드들
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // 허용할 헤더들
        config.setAllowedHeaders(Arrays.asList("*"));
        
                            // 인증 정보 허용 (쿠키, Authorization 헤더 등)
                    config.setAllowCredentials(false);
        
        // preflight 요청 캐시 시간
        config.setMaxAge(3600L);
        
        // 모든 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }
}

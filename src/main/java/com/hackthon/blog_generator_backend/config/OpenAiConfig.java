package com.hackthon.blog_generator_backend.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public OpenAiService openAiService() {
        if (apiKey == null || apiKey.trim().isEmpty() || "your-openai-api-key-here".equals(apiKey)) {
            throw new IllegalStateException("OpenAI API key is not configured. Please set 'openai.api.key' property or OPENAI_API_KEY environment variable.");
        }
        return new OpenAiService(apiKey);
    }
}

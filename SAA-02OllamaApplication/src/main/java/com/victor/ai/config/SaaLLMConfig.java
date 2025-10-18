package com.victor.ai.config;


import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(System.getenv("spring.ai.dashscope.api-key")).build();
    }

}

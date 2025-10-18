package com.victor.ai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {

//    @Value("${spring.ai.dashscope.api-key}")
//    private String apiKey;

//    @Bean
//    public DashScopeApi dashScopeApi() {
//        return DashScopeApi.builder().apiKey(apiKey).build();
//    }

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(System.getenv("spring.ai.dashscope.api-key")).build();
    }


}

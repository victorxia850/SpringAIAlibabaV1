package com.victor.ai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {

    private final String DEEPSEEK_MODEL = "deepseek-v3";

    private final String QWEN_MODEL = "qwen-max";


    @Bean(name = "deepSeek")
    public DashScopeChatModel deepseekDashScopeApi() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("spring.ai.dashscope.api-key")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }

    @Bean(name = "qWen")
    public DashScopeChatModel qwenDashScopeApi() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("spring.ai.dashscope.api-key")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }


    @Bean(name = "deepSeekClient")
    public ChatClient deepseekChatClient(@Qualifier("deepSeek") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
                .build();
    }


    @Bean(name = "qWenClient")
    public ChatClient qwenChatClient(@Qualifier("qWen") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())
                .build();
    }
}

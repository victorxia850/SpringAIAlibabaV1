package com.victor.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatClientController {

    // 方式一：构造函数注入
//    private ChatClient chatClient;
//
//    public ChatClientController(ChatModel chatModel) {
//        this.chatClient = ChatClient.builder(chatModel).build();
//    }

    // 方式二：字段注入（需要在config中定义ChatClient Bean）
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/chat/client/hello")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatClient.prompt().user(msg).call().content();
    }

    @GetMapping(value = "chat/client/stream", produces = "text/plain;charset=UTF-8")
    public Flux<String> doStream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatClient.prompt().user(msg).stream().content();
    }
}

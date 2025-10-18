package com.victor.ai.controller.chatclient;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DeepSeekChatClientController {

    @Resource(name = "deepSeekClient")
    private ChatClient chatClient;


    @GetMapping("/deepseek/chat/client/hello")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatClient.prompt().user(msg).call().content();
    }

    @GetMapping(value = "/deepseek/chat/client/stream", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> doStream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatClient.prompt().user(msg).stream().content();
    }

}

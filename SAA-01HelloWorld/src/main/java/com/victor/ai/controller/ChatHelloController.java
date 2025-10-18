package com.victor.ai.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {

    private ChatModel chatModel;

    public ChatHelloController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat/hello")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping(value = "/stream/hello", produces = "text/plain;charset=UTF-8")
    public Flux<String> doStream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.stream(msg);
    }
}

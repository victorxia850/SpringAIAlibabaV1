package com.victor.ai.controller.chatmodel;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DeepSeekChatModelController {

    @Resource(name = "deepSeek")
    private ChatModel chatModel;


    @GetMapping("/deepseek/chat/model/hello")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping(value = "/deepseek/chat/model/stream", produces = "text/event-stream")
    public Flux<String> doStream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.stream(msg);
    }

}

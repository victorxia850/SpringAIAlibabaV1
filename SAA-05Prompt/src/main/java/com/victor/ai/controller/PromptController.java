package com.victor.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class PromptController {

    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;

    @Resource(name = "qWenClient")
    private ChatClient qWenChatClient;

    @Resource(name = "deepSeek")
    private ChatModel deepSeekChatModel;


    @Resource(name = "qWen")
    private ChatModel qWenChatModel;

    @GetMapping(value = "/prompt/chat1", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat1(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return qWenChatClient.prompt()
                .system("你是一个法律助手，只能回答法律相关问题，其他问题的回复式我只能回复法律问题，其他无可奉告")
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping(value = "/prompt/chat2", produces = "application/json;charset=UTF-8")
    public Flux<ChatResponse> doChat2(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手，只能回答讲故事相关问题，其他问题的回复式我只能回复讲故事问题，其他无可奉告");
        UserMessage userMessage = new UserMessage(msg);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return deepSeekChatModel.stream(prompt);

    }


    @GetMapping(value = "/prompt/chat3", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat3(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手，只能回答讲故事相关问题，其他问题的回复式我只能回复讲故事问题，其他无可奉告");
        UserMessage userMessage = new UserMessage(msg);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return deepSeekChatModel.stream(prompt)
                .map(res -> res.getResults().get(0).getOutput().getText());
    }

    @GetMapping(value = "/prompt/chat4", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat4(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        AssistantMessage output = deepSeekChatClient.prompt()
                .system("你是一个法律助手，只能回答法律相关问题，其他问题的回复式我只能回复法律问题，其他无可奉告")
                .user(msg)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();
        return Flux.just(output.getText());
    }

    @GetMapping(value = "/prompt/chat5", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat5(@RequestParam(name = "msg", defaultValue = "北京") String city) {
        String answer = deepSeekChatClient.prompt()
                .system("你是一个天气助手，只能回答当前地区的周天气相关问题，其他问题的回复式我只能回复天气问题，其他无可奉告")
                .user(city)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
        ToolResponseMessage toolResponseMessage = new ToolResponseMessage(
                List.of(new ToolResponseMessage.ToolResponse("1", "获取天气", city))
        );
        String toolRes = toolResponseMessage.getText();
        return Flux.just(answer+toolRes);
    }
}

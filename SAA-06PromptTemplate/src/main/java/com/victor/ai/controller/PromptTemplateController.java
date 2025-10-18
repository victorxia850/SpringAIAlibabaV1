package com.victor.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class PromptTemplateController {

    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;

    @Resource(name = "qWenClient")
    private ChatClient qWenChatClient;

    @Resource(name = "deepSeek")
    private ChatModel deepSeekChatModel;


    @Resource(name = "qWen")
    private ChatModel qWenChatModel;


    // http://localhost:8086/prompt/template/chat1?topic=骑行法律问题&outPutFormat=html&wordCount=500
    @GetMapping(value = "/prompt/template/chat1", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat1(
            @RequestParam(name = "topic") String topic,
            @RequestParam(name = "outPutFormat") String outPutFormat,
            @RequestParam(name = "wordCount") String wordCount
    ) {
        PromptTemplate promptTemplate = new PromptTemplate(
                "你是一个法律助手，只能回答法律相关问题，其他问题的回复式我只能回复法律问题，其他无可奉告\n" +
                        "请根据用户的问题，生成一个法律问题的回复\n" +
                        "用户的问题是：{topic}\n" +
                        "回复的格式是：{outPutFormat}\n" +
                        "回复的字数是：{wordCount}\n"
        );
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outPutFormat", outPutFormat,
                "wordCount", wordCount
        ));
        return qWenChatClient
                .prompt(prompt)
                .stream()
                .content();
    }


    // http://localhost:8086/prompt/template/chat2?topic=骑行法律问题&outPutFormat=html&wordCount=500

    @Value("classpath:promptTemplate/template1.txt")
    private org.springframework.core.io.Resource userPromptTemplate;

    @GetMapping(value = "/prompt/template/chat2", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat2(
            @RequestParam(name = "topic") String topic,
            @RequestParam(name = "outPutFormat") String outPutFormat,
            @RequestParam(name = "wordCount") String wordCount
    ) {
        PromptTemplate promptTemplate = new PromptTemplate(userPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outPutFormat", outPutFormat,
                "wordCount", wordCount
        ));
        return deepSeekChatClient
                .prompt(prompt)
                .stream()
                .content();
    }

    // http://localhost:8086/prompt/template/chat3?sysTopic=web3%20asstant&userTopic=the%20private%20of%20solidity
    @GetMapping(value = "/prompt/template/chat3", produces = "text/plain;charset=UTF-8")
    public Flux<String> doChat3(
            @RequestParam(name = "sysTopic") String sysTopic,
            @RequestParam(name = "userTopic") String userTopic
    ) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是一个{sysTopic}助手,只能回答{sysTopic}相关问题,其他无可奉告,结果以markdown格式输出");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of(
                "sysTopic", sysTopic
        ));

        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of(
                "userTopic", userTopic
        ));

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return deepSeekChatClient
                .prompt(prompt)
                .stream()
                .content();
    }

    // http://localhost:8086/prompt/template/chat4?userMsg=解释一下solidity的私有变量
    @GetMapping(value = "/prompt/template/chat4", produces = "text/plain;charset=UTF-8")
    public String doChat4(
            @RequestParam(name = "userMsg") String userMsg
    ) {
        SystemMessage systemMessage = new SystemMessage("你是一个Java 后端开发助手,只能回答Java 后端相关问题,其他无可奉告,结果以markdown格式输出");

        UserMessage userMessage = new UserMessage(userMsg);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return qWenChatModel.call(prompt).getResult().getOutput().getText();
    }


}

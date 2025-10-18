package com.victor.ai.controller;

import com.victor.ai.record.Student;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;


@RestController
public class StructuredOutputController {

    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;

    @Resource(name = "qWenClient")
    private ChatClient qWenChatClient;

    @Resource(name = "deepSeek")
    private ChatModel deepSeekChatModel;


    @Resource(name = "qWen")
    private ChatModel qWenChatModel;


    // http://localhost:8087/structured/output/chat1?name=%E5%BC%A0%E4%B8%89&email=san.zhang@qq.com
    @GetMapping(value = "/structured/output/chat1", produces = "application/json;charset=UTF-8")
    public Student doChat1(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email
    ) {
        return qWenChatClient.prompt().user(new Consumer<ChatClient.PromptUserSpec>() {
            @Override
            public void accept(ChatClient.PromptUserSpec promptUserSpec) {
                promptUserSpec.text("你好，我的学号是0001，名字是{name}，专业是计算机科学，邮箱是{email}。")
                        .param("name", name)
                        .param("email", email)
                ;
            }
        }).call().entity(Student.class);
    }

    // http://localhost:8087/structured/output/chat2?name=%E5%BC%A0%E4%B8%89&email=san.zhang@qq.com
    @GetMapping(value = "/structured/output/chat2", produces = "application/json;charset=UTF-8")
    public Student doChat2(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email
    ) {
        String template = "你好，我的学号是0002，名字是{name}，专业是网络工程，邮箱是{email}。";
        return deepSeekChatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(template)
                        .param("name", name)
                        .param("email", email))
                .call()
                .entity(Student.class);
    }

}
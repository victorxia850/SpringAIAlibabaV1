package com.victor.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;


@RestController
public class PersistentController {

    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;

    @Resource(name = "qWenClient")
    private ChatClient qWenChatClient;


    // http://localhost:8088/persistent/chat1?msg=%E5%86%8D%E5%8A%A02%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91&userId=001
    @GetMapping(value = "/persistent/chat1", produces = "application/json;charset=UTF-8")
    public String doChat1(
            @RequestParam(name = "msg") String msg,
            @RequestParam(name = "userId") String usrId
    ) {
        return qWenChatClient
                .prompt(msg)
                .advisors(new Consumer<ChatClient.AdvisorSpec>() {
                    @Override
                    public void accept(ChatClient.AdvisorSpec advisorSpec) {
                        advisorSpec.param(ChatMemory.CONVERSATION_ID, usrId);
                    }
                })
                .call()
                .content();
    }

    @GetMapping(value = "/persistent/chat2", produces = "application/json;charset=UTF-8")
    public String doChat2(
            @RequestParam(name = "msg") String msg,
            @RequestParam(name = "userId") String usrId
    ) {
        return qWenChatClient
                .prompt(msg)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, usrId))
                .call()
                .content();
    }


}
package ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.MacSpi;
import java.util.function.Consumer;


@RestController
public class NoFunctionCallingController {


    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;



    @GetMapping(value = "/no/function/calling/chat1", produces = "application/json;charset=UTF-8")
    public String doChat1(
            @RequestParam(name = "msg", defaultValue = "你是谁？现在几点？") String msg
    ) {
        return deepSeekChatClient.prompt().user(msg).call().content();    }



}
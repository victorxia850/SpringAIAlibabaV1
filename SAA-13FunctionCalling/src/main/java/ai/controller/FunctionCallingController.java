package ai.controller;

import ai.tools.DateTimeTools;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FunctionCallingController {


    @Resource(name = "deepSeekClient")
    private ChatClient deepSeekChatClient;

    @Resource(name = "qWen")
    private ChatModel qWenChatModel;



    @GetMapping(value = "/function/calling/chat1", produces = "application/json;charset=UTF-8")
    public String doChat1(
            @RequestParam(name = "msg", defaultValue = "你是谁？现在几点？") String msg) {
        ToolCallback[] from = ToolCallbacks.from(new DateTimeTools());
        ChatOptions toolCallingChatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(from)
                .build();
        Prompt prompt = new Prompt(msg, toolCallingChatOptions);
        return deepSeekChatClient.prompt(prompt).user(msg).call().content();    }


    @GetMapping(value = "/function/calling/chat2", produces = "application/json;charset=UTF-8")
    public String doChat2(
            @RequestParam(name = "msg", defaultValue = "你是谁？现在几点？") String msg) {
        ToolCallback[] from = ToolCallbacks.from(new DateTimeTools());
        ChatOptions toolCallingChatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(from)
                .build();
        Prompt prompt = new Prompt(msg, toolCallingChatOptions);
        return qWenChatModel.call(prompt).getResult().getOutput().getText();
    }

}
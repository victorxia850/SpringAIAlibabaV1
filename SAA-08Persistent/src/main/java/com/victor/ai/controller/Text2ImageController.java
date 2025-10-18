package com.victor.ai.controller;


import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Text2ImageController {

    private final String ImageModel = "wanx2.1-t2i-turbo";

    @Resource
    private ImageModel imageModel;

    // http://localhost:8088/image/chat1?msg=%E7%A7%91%E6%AF%94
    @GetMapping(value = "/image/chat1", produces = "application/json;charset=UTF-8")
    public String doChat1(
            @RequestParam(name = "msg") String msg
    ) {
        return imageModel
                .call(
                        new ImagePrompt(msg, DashScopeImageOptions.builder().withModel(ImageModel).build())
                )
                .getResult()
                .getOutput()
                .getUrl();
    }

}

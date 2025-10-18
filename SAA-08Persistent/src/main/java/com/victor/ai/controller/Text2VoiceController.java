package com.victor.ai.controller;


import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

@RestController
public class Text2VoiceController {

    private final String VoiceModel = "cosyvoice-v2";

    private final String VoiceType = "longyingcui";

    @Resource
    private SpeechSynthesisModel speechSynthesisModel;

    // http://localhost:8088/voice/chat1?msg=%E6%94%AF%E4%BB%98%E5%AE%9D%E5%88%B0%E8%B4%A61000000%E5%85%83
    @GetMapping(value = "/voice/chat1", produces = "application/json;charset=UTF-8")
    public String doChat1(
            @RequestParam(name = "msg") String msg
    ) {
        String file = "D:\\"+ UUID.randomUUID()+".mp3";

        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .model(VoiceModel)
                .voice(VoiceType)
                .build();
        SpeechSynthesisResponse call = speechSynthesisModel.call(new SpeechSynthesisPrompt(msg, options));

        ByteBuffer audio = call.getResult().getOutput().getAudio();

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(audio.array());
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

}

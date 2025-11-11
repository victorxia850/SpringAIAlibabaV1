package com.victor.ai.controller;



import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;


@RestController
public class Embed2VectorController {


    @Resource
    private EmbeddingModel embeddingModel;


    @Resource
    private VectorStore vectorStore;

    // http://localhost:8088/voice/chat1?msg=%E6%94%AF%E4%BB%98%E5%AE%9D%E5%88%B0%E8%B4%A61000000%E5%85%83
    @GetMapping(value = "/text/embed", produces = "application/json;charset=UTF-8")
    public EmbeddingResponse text2Embedding(@RequestParam(name = "msg") String msg) {

        // 文本转换为向量并打印
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }

    @GetMapping(value = "/text/embed/add", produces = "application/json;charset=UTF-8")
    public void addTextEmbedding(@RequestParam(name = "msg") String msg) {
       List<Document> documents = Arrays.asList(new Document(msg));
        vectorStore.add(documents);
    }

    @GetMapping(value = "/text/embed/search", produces = "application/json;charset=UTF-8")
    public List<Document> searchTextEmbedding(@RequestParam(name = "msg") String msg) {
        SearchRequest searchRequest =  SearchRequest.builder()
                .query(msg)
                .topK(5)
                .build();
        return vectorStore.similaritySearch(searchRequest);

    }




}
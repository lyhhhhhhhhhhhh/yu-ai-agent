package com.lyh.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-30-11:18
 **/

@Configuration
public class MovieAppVectorStoreConfig {

    @Resource
    private MovieAppJSONLoader movieAppJSONLoader;

    @Bean
    VectorStore movieAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        List<Document> loadJson = movieAppJSONLoader.loadJson();
        simpleVectorStore.add(loadJson);
        return simpleVectorStore;
    }
}

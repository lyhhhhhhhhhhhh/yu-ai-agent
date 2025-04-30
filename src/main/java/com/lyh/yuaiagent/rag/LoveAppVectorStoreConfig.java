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
 * @time 2025-04-29-16:05
 **/

/**
 * 恋爱大师向量数据库配置 基于内存的初始化向量的Bean
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        List<Document> documentList = loveAppDocumentLoader.loadMarkdown();
        simpleVectorStore.add(documentList);
        return simpleVectorStore;
    }

}

//https://docs.spring.io/spring-ai/reference/api/embeddings.html
//https://docs.spring.io/spring-ai/reference/api/embeddings/openai-embeddings.html
//https://docs.spring.io/spring-ai/reference/api/embeddings/ollama-embeddings.html

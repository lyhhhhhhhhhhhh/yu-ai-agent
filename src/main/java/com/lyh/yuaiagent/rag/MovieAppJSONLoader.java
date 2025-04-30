package com.lyh.yuaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-30-11:11
 **/

@Component
@Slf4j
public class MovieAppJSONLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    public MovieAppJSONLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
        // 创建递归字符文本分割器，设置块大小和重叠大小
    }

    // private final Resource resource;
    //
    // public MovieAppJSONLoader(@Value("classpath:MovieRag/MovieList.json")Resource
    // resource) {
    // this.resource = resource;
    // }

    public List<Document> loadJson() {
        ArrayList<Document> allJsonList = new ArrayList<>();
        // 加载多篇 JSON 文件
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:MovieRag/*.json");
            for (Resource resource : resources) {
                JsonReader jsonReader = new JsonReader(resource, "data");
                allJsonList.addAll(jsonReader.get());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allJsonList;
    }

}

package com.lyh.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class LoveAppDocumentLoaderTest {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MovieAppJSONLoader movieAppJSONLoader;

    @Test
    void loadMarkdown() {
        loveAppDocumentLoader.loadMarkdown();
    }

    @Test
    void loadJson() {
        movieAppJSONLoader.loadJson();
    }


}
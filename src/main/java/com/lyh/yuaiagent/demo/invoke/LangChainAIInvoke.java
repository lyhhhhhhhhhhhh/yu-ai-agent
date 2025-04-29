package com.lyh.yuaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-27-15:17
 **/

public class LangChainAIInvoke {

    public static void main(String[] args) {
        ChatLanguageModel chatLanguageModel = QwenChatModel.builder()
                .apiKey(TestAPIKey.API_KEY)
                .modelName("qwen-max")
                .build();
        String answer = chatLanguageModel.chat("你好");
        System.out.println(answer);

    }

}

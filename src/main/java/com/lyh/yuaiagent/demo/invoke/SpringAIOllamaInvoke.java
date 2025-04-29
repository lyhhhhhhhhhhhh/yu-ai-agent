package com.lyh.yuaiagent.demo.invoke;

import cn.hutool.socket.aio.AioClient;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-27-14:58
 * Spring AI框架调用AI 大模型
 **/

//@Component
public class SpringAIOllamaInvoke implements CommandLineRunner {

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = ollamaChatModel.call(new Prompt("介绍你自己")).getResult().getOutput();
        System.out.println(output);
    }
}

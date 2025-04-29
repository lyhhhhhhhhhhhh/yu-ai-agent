package com.lyh.yuaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-27-14:58
 * Spring AI框架调用AI 大模型
 **/

//@Component
public class SpringAIInvoke implements CommandLineRunner {


    @Resource
    private ChatModel dashscopeChatModel;

    record ActorFilms(String actor, List<String> movies) {}
    // 用 class 定义

    @Override
    public void run(String... args) throws Exception {
        //AssistantMessage output = dashscopeChatModel.call(new Prompt("介绍你自己")).getResult().getOutput();
        //System.out.println(output);
        ChatClient chatClient = ChatClient.create(dashscopeChatModel);
        ActorFilms actorFilms = chatClient.prompt()
                .user("Generate the filmography for a random actor.")
                .call()
                .entity(ActorFilms.class);
        System.out.println(actorFilms);
    }
}

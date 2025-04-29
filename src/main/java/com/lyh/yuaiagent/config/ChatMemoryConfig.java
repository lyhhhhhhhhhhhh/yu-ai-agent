package com.lyh.yuaiagent.config;

import com.lyh.yuaiagent.chatMemory.MysqlChatMemory;
import com.lyh.yuaiagent.service.ChatMessageService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对话记忆配置类
 */
@Configuration
public class ChatMemoryConfig {

  /**
   * 这样写的好处就是 因为现在这里只用到了一个对话记忆
   * 如果有多个 需要切换 或者由用户选择 那么我们可以在代码中动态切换 不用注入那么多@Bean
   *
   * @param chatMessageService
   * @return
   */
  @Bean
  public ChatMemory chatMemory(ChatMessageService chatMessageService) {
    return new MysqlChatMemory(chatMessageService);
  }
}
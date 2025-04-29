package com.lyh.yuaiagent.app;

import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.entity.Movieorder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * @author liyuhang
 * @version 1.0
 * @time 2025-04-28-20:09
 **/

@Component
@Slf4j
public class MovieApp {

        private static final String SYSTEM_PROMPT = "你是一个专业的电影推荐官。\n" +
                        "根据用户的历史观影记录和当前可选的电影列表，请推荐 4 部用户可能感兴趣的电影。" +
                        "简要说明每一部推荐电影的推荐理由（例如：类型相似、题材风格相似等）。";

        private final ChatClient chatClient;

        /**
         * 初始化ChatClient，使用MySQL对话记忆
         * 
         * @param openAiChatModel AI模型
         * @param chatMemory      对话记忆（由Spring注入MySQL实现）
         */
        public MovieApp(ChatModel openAiChatModel, ChatMemory chatMemory) {
                chatClient = ChatClient.builder(openAiChatModel)
                                .defaultSystem(SYSTEM_PROMPT)
                                .defaultAdvisors(
                                                new MessageChatMemoryAdvisor(chatMemory))
                                .build();
                log.info("MovieApp初始化完成，使用MySQL持久化对话记忆");
        }

        public record MovieList(List<Movie> reCommandList) {
        }

        /**
         * 推荐电影列表
         * 
         * @param chatId         对话ID
         * @param movieList      可选电影列表
         * @param movieorderList 用户观影记录
         * @return 推荐电影列表
         */
        public MovieList recommendMovies(String chatId, List<Movie> movieList, List<Movieorder> movieorderList) {
                // 使用更简洁的提示构建方式
                StringBuilder prompt = new StringBuilder();

                // 仅包含必要的订单信息
                prompt.append("订单记录ID: ");
                if (movieorderList != null && !movieorderList.isEmpty()) {
                        for (Movieorder order : movieorderList) {
                                prompt.append(order.getMovieid()).append(" ");
                        }
                } else {
                        prompt.append("无");
                }

                // 仅包含必要的电影信息
                prompt.append("\n电影列表: ");
                if (movieList != null && !movieList.isEmpty()) {
                        for (Movie movie : movieList) {
                                prompt.append(movie.getId()).append("(").append(movie.getMovietitle())
                                                .append(",").append(movie.getMovietype()).append(") ");
                        }
                } else {
                        prompt.append("无");
                }

                prompt.append("\n请推荐4部电影");

                MovieList list = chatClient
                                .prompt()
                                .system(SYSTEM_PROMPT)
                                .user(prompt.toString())
                                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                                .call()
                                .entity(MovieList.class);
                log.info("电影推荐结果: chatId={}, 推荐数量={}", chatId, list.reCommandList().size());
                return list;
        }
}

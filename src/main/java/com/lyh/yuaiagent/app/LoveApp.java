package com.lyh.yuaiagent.app;

import com.lyh.yuaiagent.advisor.MyLoggerAdvisor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {

        private final ChatClient chatClient;

        private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
                        "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
                        "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
                        "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";

        /**
         * 初始化 ChatClient，使用MySQL对话记忆实现
         * 
         * @param openAiChatModel AI模型
         * @param chatMemory      对话记忆（由Spring注入MySQL实现）
         */
        public LoveApp(ChatModel openAiChatModel, ChatMemory chatMemory) {
                chatClient = ChatClient.builder(openAiChatModel)
                                .defaultSystem(SYSTEM_PROMPT)
                                .defaultAdvisors(
                                                new MessageChatMemoryAdvisor(chatMemory),
                                                new MyLoggerAdvisor())
                                .build();
                log.info("LoveApp初始化完成，使用MySQL持久化对话记忆");
        }

        /**
         * AI 基础对话 (支持多轮对话记忆)
         * 
         * @param message 用户消息
         * @param chatId  对话ID
         * @return AI回复内容
         */
        public String doChat(String message, String chatId) {
                ChatResponse response = chatClient
                                .prompt()
                                .user(message)
                                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                                .call()
                                .chatResponse();
                String content = response.getResult().getOutput().getText();
                log.info("AI回复: chatId={}, content={}", chatId, content);
                return content;
        }

        record LoveReport(String title, List<String> suggestions) {
        }

        /**
         * AI 基础对话 (实战结构化输出)
         * 
         * @param message 用户消息
         * @param chatId  对话ID
         * @return 恋爱报告
         */
        public LoveReport doChatWithReport(String message, String chatId) {
                LoveReport loveReport = chatClient
                                .prompt()
                                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果报告，标题为{用户名}的恋爱报告，内容为建议列表")
                                .user(message)
                                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                                .call()
                                .entity(LoveReport.class);
                log.info("生成恋爱报告: chatId={}, report={}", chatId, loveReport);
                return loveReport;
        }
}

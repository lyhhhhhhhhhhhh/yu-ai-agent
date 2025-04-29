package com.lyh.yuaiagent.chatMemory;

import com.lyh.yuaiagent.common.MessageType;
import com.lyh.yuaiagent.entity.ChatMessage;
import com.lyh.yuaiagent.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于MySQL的对话记忆实现
 */
@Slf4j
@RequiredArgsConstructor
/**
 * 这里已经注入到Spring容器当中 ChatMemoryConfig这里完成的
 */
public class MysqlChatMemory implements ChatMemory {

    /**
     * 这里的注入方式由lombok完成的
     *
     * public MysqlChatMemory(ChatMessageService chatMessageService) {
     *     this.chatMessageService = chatMessageService;
     * }
     *
     * 相当于这样的
     * 当Spring创建MysqlChatMemory实例时：
     * 它发现这个类需要一个ChatMessageService类型的依赖
     * Spring会在容器中查找ChatMessageService类型的Bean（按类型匹配，不是按名称）
     * 找到后，将这个Bean实例通过构造函数注入到MysqlChatMemory中
     *
     */
    private final ChatMessageService chatMessageService;

    @Override
    public List<Message> get(String conversationId, int limit) {
        log.info("获取对话历史: conversationId={}, limit={}", conversationId, limit);

        List<ChatMessage> chatMessages = chatMessageService.getMessagesByChatId(conversationId, limit);
        return chatMessages.stream()
                .map(this::convertToMessage)
                .collect(Collectors.toList());
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        log.info("批量添加对话消息: conversationId={}, messagesCount={}", conversationId, messages.size());

        for (Message message : messages) {
            add(conversationId, message);
        }
    }

    @Override
    public void add(String conversationId, Message message) {
        log.info("添加对话消息: conversationId={}, messageType={}", conversationId, message.getClass().getSimpleName());

        MessageType type;
        if (message instanceof UserMessage) {
            type = MessageType.USER;
        } else if (message instanceof AssistantMessage) {
            type = MessageType.ASSISTANT;
        } else if (message instanceof SystemMessage) {
            type = MessageType.SYSTEM;
        } else {
            log.warn("未知消息类型: {}", message.getClass().getName());
            return;
        }

        chatMessageService.saveMessage(conversationId, type, message.getText());
    }

    @Override
    public void clear(String conversationId) {
        log.info("清除对话历史: conversationId={}", conversationId);

        // 删除所有消息
        chatMessageService.lambdaUpdate()
                .eq(ChatMessage::getChatId, conversationId)
                .remove();
    }

    /**
     * 将数据库消息实体转换为Spring AI消息对象
     */
    private Message convertToMessage(ChatMessage chatMessage) {
        String content = chatMessage.getMessageContent();
        MessageType type = MessageType.valueOf(chatMessage.getMessageType());

        return switch (type) {
            case USER -> new UserMessage(content);
            case ASSISTANT -> new AssistantMessage(content);
            case SYSTEM -> new SystemMessage(content);
        };
    }
}
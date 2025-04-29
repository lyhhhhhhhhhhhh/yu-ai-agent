package com.lyh.yuaiagent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.yuaiagent.common.MessageType;
import com.lyh.yuaiagent.entity.ChatMessage;
import com.lyh.yuaiagent.mapper.ChatMessageMapper;
import com.lyh.yuaiagent.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对话消息Service实现类
 */
@Service
@Slf4j
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

  @Override
  public void saveMessage(String chatId, MessageType type, String content) {
    ChatMessage message = new ChatMessage()
        .setChatId(chatId)
        .setMessageType(type.name())
        .setMessageContent(content);
    save(message);
    log.info("保存对话消息: chatId={}, type={}, contentLength={}", chatId, type, content.length());
  }

  @Override
  public List<ChatMessage> getMessagesByChatId(String chatId, Integer limit) {
    LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ChatMessage::getChatId, chatId)
        .orderByAsc(ChatMessage::getCreateTime)
        .last(limit != null, "LIMIT " + limit);
    List<ChatMessage> messages = list(wrapper);
    log.info("获取对话历史消息: chatId={}, count={}", chatId, messages.size());
    return messages;
  }
}
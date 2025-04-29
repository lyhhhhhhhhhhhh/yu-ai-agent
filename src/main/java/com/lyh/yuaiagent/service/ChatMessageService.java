package com.lyh.yuaiagent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.yuaiagent.common.MessageType;
import com.lyh.yuaiagent.entity.ChatMessage;

import java.util.List;

/**
 * 对话消息Service接口
 */
public interface ChatMessageService extends IService<ChatMessage> {

  /**
   * 保存对话消息
   * 
   * @param chatId  对话ID
   * @param type    消息类型
   * @param content 消息内容
   */
  void saveMessage(String chatId, MessageType type, String content);

  /**
   * 根据对话ID获取历史消息列表
   * 
   * @param chatId 对话ID
   * @param limit  获取条数限制
   * @return 消息列表
   */
  List<ChatMessage> getMessagesByChatId(String chatId, Integer limit);
}
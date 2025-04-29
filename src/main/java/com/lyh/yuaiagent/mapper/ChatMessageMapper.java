package com.lyh.yuaiagent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.yuaiagent.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对话消息Mapper接口
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
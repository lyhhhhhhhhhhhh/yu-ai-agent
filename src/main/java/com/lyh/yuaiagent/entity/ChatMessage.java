package com.lyh.yuaiagent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import groovy.transform.Field;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 对话消息存储表
 * 
 * @TableName chat_message
 */
@TableName(value = "chat_message")
@Data
@Accessors(chain = true)
public class ChatMessage implements Serializable {
  /**
   * 主键ID
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 对话ID
   */
  @TableField("chat_id")
  private String chatId;

  /**
   * 消息类型：USER/ASSISTANT/SYSTEM
   */
  @TableField("message_type")
  private String messageType;

  /**
   * 消息内容
   */
  @TableField("message_content")
  private String messageContent;

  /**
   * 创建时间
   */
  @TableField("create_time")
  private Date createTime;

  /**
   * 更新时间
   */
  @TableField("update_time")
  private Date updateTime;

  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}
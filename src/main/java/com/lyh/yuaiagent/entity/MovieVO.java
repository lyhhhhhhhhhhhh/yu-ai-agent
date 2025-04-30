package com.lyh.yuaiagent.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 电影
 * @TableName movie
 */
@Data
public class MovieVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 电影名称
     */
    private String movietitle;

    /**
     * 电影类型
     */
    private String movietype;

    /**
     * 推荐理由
     */
    private String recommendReason;

}
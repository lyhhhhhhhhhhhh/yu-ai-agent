package com.lyh.yuaiagent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 电影
 * @TableName movie
 */
@TableName(value ="movie")
@Data
public class Movie implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 电影时长
     */
    private Integer movieduration;

    /**
     * 电影图片
     */
    private String moviepicture;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 编辑时间
     */
    private Date edittime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    /**
     * 电影评分
     */
    private BigDecimal movierating;

    /**
     * 地区
     */
    private String movieregion;

    /**
     * 电影剧情简介
     */
    private String moviesynopsis;

    /**
     * 推荐理由
     */
    @TableField(exist = false)
    private String recommendReason;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
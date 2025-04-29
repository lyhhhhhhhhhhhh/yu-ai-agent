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
 * 电影订单表
 * @TableName MovieOrder
 */
@TableName(value ="MovieOrder")
@Data
public class Movieorder implements Serializable {
    /**
     * 订单 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 id
     */
    private Long userid;

    /**
     * 电影 id
     */
    private Long movieid;

    /**
     * 电影名称
     */
    private String moviename;

    /**
     * 影院 id
     */
    private Long cinemaid;

    /**
     * 影院名称
     */
    private String cinemaname;

    /**
     * 放映厅名称
     */
    private String cinemahallname;

    /**
     * 场次 id
     */
    private Long moviesessionid;

    /**
     * 放映时间
     */
    private Date movieshowtime;

    /**
     * 座位信息 (存储座位号数组)
     */
    private Object seatinfo;

    /**
     * 订单总金额
     */
    private BigDecimal totalprice;

    /**
     * 是否支付 (0-未支付, 1-已支付)
     */
    private Integer ispayfor;

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
    private Integer isdeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
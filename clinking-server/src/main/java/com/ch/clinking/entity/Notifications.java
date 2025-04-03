package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Data
@ToString
@TableName("`notifications`")
public class Notifications implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("shopId")
    private String shopId;

    //消息类型
    @TableField("type")
    private String type;

    //消息提示内容
    @TableField("infoMessage")
    private String infoMessage;

    //确认状态
    @TableField("isConfirmed")
    private Integer isConfirmed;

    //创建时间
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    //消息类型
    @TableField("spuId")
    private String spuId = "";

    @TableField("skcId")
    private String skcId = "";

    @TableField("remark")
    private String remark = "";

    //确认时间
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmTime;

//    public Notifications(Integer userId, String shopId, String type,  String infoMessage, Date createdTime, String productNumber, String skcId, String remark) {
//        this.userId = userId;
//        this.shopId = shopId;
//        this.type = type;
//        this.infoMessage = infoMessage;
//        this.isConfirmed = 0;
//        this.createdTime = createdTime;
//        this.confirmTime = createdTime;
//        this.skcId = skcId;
//        this.spuId = productNumber;
//        this.remark = remark;
//    }
}

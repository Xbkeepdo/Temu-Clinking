package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("user_shop")
public class UserShop implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String shopId;

}

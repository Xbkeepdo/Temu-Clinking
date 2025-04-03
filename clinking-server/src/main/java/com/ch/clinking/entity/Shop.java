package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("shop")
public class Shop {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //店铺ID为创建者的手机号码
    private String shopId;
    //商户类型
    private String shopType;
    //店铺名称
    private String shopName;
    //店铺头像
    private String shopImage;
    //超级管理员昵称
    private String superAdminNickName;
    //超级管理员用户名（账号）
    private String superAdminAccount;

    private String accessToken;

    private String appKey;

    private String appSecret;

    private int totalSale;

    private int status;

    private String remark;
}

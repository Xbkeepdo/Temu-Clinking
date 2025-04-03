package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("`sales_history_shop_brief`")
public class SalesHistoryShopBrief implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("shopId")
    private String shopId;

    @TableField("shopName")
    private String shopName;

    @TableField("shopImage")
    private String shopImage;

    @TableField("superAdminAccount")
    private String superAdminAccount="";

    @TableField("periodsTimeType")
    private String periodsTimeType="";

    @TableField("salesNum")
    private Integer salesNum=0;

    @TableField("salesProfits")
    private double salesProfits=0;

    @TableField("createdTime")
    private Date createdTime;

}

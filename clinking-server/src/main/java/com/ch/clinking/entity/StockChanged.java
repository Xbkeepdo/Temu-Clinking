package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("stock_changed")
public class StockChanged implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("skcId")
    private String skcId;

    //用户名
    @TableField("account")
    private String account;

    @TableField("nick_name")
    private String nickName;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField("remark")
    private String remark;

    private int change_XXS = 0;
    private int change_XS = 0;
    private int change_S = 0;
    private int change_M = 0;
    private int change_L = 0;
    private int change_XL = 0;
    private int change_XXL = 0;

    private int change_Plus_0XL = 0;
    private int change_Plus_1XL = 0;
    private int change_Plus_2XL = 0;
    private int change_Plus_3XL = 0;
    private int change_Plus_4XL = 0;
    private int change_Plus_5XL = 0;
    private int change_Plus_6XL = 0;

    private int inHouseStock_XXS = 0;
    private int inHouseStock_XS = 0;
    private int inHouseStock_S = 0;
    private int inHouseStock_M = 0;
    private int inHouseStock_L = 0;
    private int inHouseStock_XL = 0;
    private int inHouseStock_XXL = 0;

    private int inHouseStock_Plus_0XL = 0;
    private int inHouseStock_Plus_1XL = 0;
    private int inHouseStock_Plus_2XL = 0;
    private int inHouseStock_Plus_3XL = 0;
    private int inHouseStock_Plus_4XL = 0;
    private int inHouseStock_Plus_5XL = 0;
    private int inHouseStock_Plus_6XL = 0;

    private boolean activate_XXS = false;
    private boolean activate_XS = false;
    private boolean activate_S = false;
    private boolean activate_M = false;
    private boolean activate_L = false;
    private boolean activate_XL = false;
    private boolean activate_XXL = false;

    private boolean activate_Plus_0XL = false;
    private boolean activate_Plus_1XL = false;
    private boolean activate_Plus_2XL = false;
    private boolean activate_Plus_3XL = false;
    private boolean activate_Plus_4XL = false;
    private boolean activate_Plus_5XL = false;
    private boolean activate_Plus_6XL = false;

}

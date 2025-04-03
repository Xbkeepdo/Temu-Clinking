package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@ToString
@TableName("`order`")
public class Order implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String shopId;

    //订单编号
    private String orderId;
    //SkcId
    private String skcId;
    //状态
    private String state;

    @TableField(select = false,exist = false)
    private String title;

    @TableField(select = false,exist = false)
    private String firstImage = "default.png";

    @TableField(select = false,exist = false)
    private String productNumber="";

    //创建时间
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    //更新时间
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String remark;

    @JsonProperty("XXS")
    private int XXS = 0;

    @JsonProperty("XS")
    private int XS = 0;

    @JsonProperty("S")
    private int S = 0;

    @JsonProperty("M")
    private int M = 0;

    @JsonProperty("L")
    private int L = 0;

    @JsonProperty("XL")
    private int XL = 0;

    @JsonProperty("XXL")
    private int XXL = 0;

    @JsonProperty("Plus_0XL")
    private int Plus_0XL = 0;

    @JsonProperty("Plus_1XL")
    private int Plus_1XL = 0;

    @JsonProperty("Plus_2XL")
    private int Plus_2XL = 0;

    @JsonProperty("Plus_3XL")
    private int Plus_3XL = 0;

    @JsonProperty("Plus_4XL")
    private int Plus_4XL = 0;

    @JsonProperty("Plus_5XL")
    private int Plus_5XL = 0;

    @JsonProperty("Plus_6XL")
    private int Plus_6XL = 0;

    @JsonProperty("make_XXS")
    private int make_XXS = 0;

    @JsonProperty("make_XS")
    private int make_XS = 0;

    @JsonProperty("make_S")
    private int make_S = 0;

    @JsonProperty("make_M")
    private int make_M = 0;

    @JsonProperty("make_L")
    private int make_L = 0;

    @JsonProperty("make_XL")
    private int make_XL = 0;

    @JsonProperty("make_XXL")
    private int make_XXL = 0;

    @JsonProperty("make_Plus_0XL")
    private int make_Plus_0XL = 0;

    @JsonProperty("make_Plus_1XL")
    private int make_Plus_1XL = 0;

    @JsonProperty("make_Plus_2XL")
    private int make_Plus_2XL = 0;

    @JsonProperty("make_Plus_3XL")
    private int make_Plus_3XL = 0;

    @JsonProperty("make_Plus_4XL")
    private int make_Plus_4XL = 0;

    @JsonProperty("make_Plus_5XL")
    private int make_Plus_5XL = 0;

    @JsonProperty("make_Plus_6XL")
    private int make_Plus_6XL = 0;

    @JsonProperty("activate_XXS")
    private int activate_XXS = 0;

    @JsonProperty("activate_XS")
    private int activate_XS = 0;

    @JsonProperty("activate_S")
    private int activate_S = 0;

    @JsonProperty("activate_M")
    private int activate_M = 0;

    @JsonProperty("activate_L")
    private int activate_L = 0;

    @JsonProperty("activate_XL")
    private int activate_XL = 0;

    @JsonProperty("activate_XXL")
    private int activate_XXL = 0;

    @JsonProperty("activate_Plus_0XL")
    private int activate_Plus_0XL = 0;

    @JsonProperty("activate_Plus_1XL")
    private int activate_Plus_1XL = 0;

    @JsonProperty("activate_Plus_2XL")
    private int activate_Plus_2XL = 0;

    @JsonProperty("activate_Plus_3XL")
    private int activate_Plus_3XL = 0;

    @JsonProperty("activate_Plus_4XL")
    private int activate_Plus_4XL = 0;

    @JsonProperty("activate_Plus_5XL")
    private int activate_Plus_5XL = 0;

    @JsonProperty("activate_Plus_6XL")
    private int activate_Plus_6XL = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getXXS() {
        return XXS;
    }

    public void setXXS(int XXS) {
        this.XXS = XXS;
    }

    public int getXS() {
        return XS;
    }

    public void setXS(int XS) {
        this.XS = XS;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getXL() {
        return XL;
    }

    public void setXL(int XL) {
        this.XL = XL;
    }

    public int getXXL() {
        return XXL;
    }

    public void setXXL(int XXL) {
        this.XXL = XXL;
    }

    public int getPlus_0XL() {
        return Plus_0XL;
    }

    public void setPlus_0XL(int plus_0XL) {
        Plus_0XL = plus_0XL;
    }

    public int getPlus_1XL() {
        return Plus_1XL;
    }

    public void setPlus_1XL(int plus_1XL) {
        Plus_1XL = plus_1XL;
    }

    public int getPlus_2XL() {
        return Plus_2XL;
    }

    public void setPlus_2XL(int plus_2XL) {
        Plus_2XL = plus_2XL;
    }

    public int getPlus_3XL() {
        return Plus_3XL;
    }

    public void setPlus_3XL(int plus_3XL) {
        Plus_3XL = plus_3XL;
    }

    public int getPlus_4XL() {
        return Plus_4XL;
    }

    public void setPlus_4XL(int plus_4XL) {
        Plus_4XL = plus_4XL;
    }

    public int getPlus_5XL() {
        return Plus_5XL;
    }

    public void setPlus_5XL(int plus_5XL) {
        Plus_5XL = plus_5XL;
    }

    public int getPlus_6XL() {
        return Plus_6XL;
    }

    public void setPlus_6XL(int plus_6XL) {
        Plus_6XL = plus_6XL;
    }

    public int getMake_XXS() {
        return make_XXS;
    }

    public void setMake_XXS(int make_XXS) {
        this.make_XXS = make_XXS;
    }

    public int getMake_XS() {
        return make_XS;
    }

    public void setMake_XS(int make_XS) {
        this.make_XS = make_XS;
    }

    public int getMake_S() {
        return make_S;
    }

    public void setMake_S(int make_S) {
        this.make_S = make_S;
    }

    public int getMake_M() {
        return make_M;
    }

    public void setMake_M(int make_M) {
        this.make_M = make_M;
    }

    public int getMake_L() {
        return make_L;
    }

    public void setMake_L(int make_L) {
        this.make_L = make_L;
    }

    public int getMake_XL() {
        return make_XL;
    }

    public void setMake_XL(int make_XL) {
        this.make_XL = make_XL;
    }

    public int getMake_XXL() {
        return make_XXL;
    }

    public void setMake_XXL(int make_XXL) {
        this.make_XXL = make_XXL;
    }

    public int getMake_Plus_0XL() {
        return make_Plus_0XL;
    }

    public void setMake_Plus_0XL(int make_Plus_0XL) {
        this.make_Plus_0XL = make_Plus_0XL;
    }

    public int getMake_Plus_1XL() {
        return make_Plus_1XL;
    }

    public void setMake_Plus_1XL(int make_Plus_1XL) {
        this.make_Plus_1XL = make_Plus_1XL;
    }

    public int getMake_Plus_2XL() {
        return make_Plus_2XL;
    }

    public void setMake_Plus_2XL(int make_Plus_2XL) {
        this.make_Plus_2XL = make_Plus_2XL;
    }

    public int getMake_Plus_3XL() {
        return make_Plus_3XL;
    }

    public void setMake_Plus_3XL(int make_Plus_3XL) {
        this.make_Plus_3XL = make_Plus_3XL;
    }

    public int getMake_Plus_4XL() {
        return make_Plus_4XL;
    }

    public void setMake_Plus_4XL(int make_Plus_4XL) {
        this.make_Plus_4XL = make_Plus_4XL;
    }

    public int getMake_Plus_5XL() {
        return make_Plus_5XL;
    }

    public void setMake_Plus_5XL(int make_Plus_5XL) {
        this.make_Plus_5XL = make_Plus_5XL;
    }

    public int getMake_Plus_6XL() {
        return make_Plus_6XL;
    }

    public void setMake_Plus_6XL(int make_Plus_6XL) {
        this.make_Plus_6XL = make_Plus_6XL;
    }

    public int getActivate_XXS() {
        return activate_XXS;
    }

    public void setActivate_XXS(int activate_XXS) {
        this.activate_XXS = activate_XXS;
    }

    public int getActivate_XS() {
        return activate_XS;
    }

    public void setActivate_XS(int activate_XS) {
        this.activate_XS = activate_XS;
    }

    public int getActivate_S() {
        return activate_S;
    }

    public void setActivate_S(int activate_S) {
        this.activate_S = activate_S;
    }

    public int getActivate_M() {
        return activate_M;
    }

    public void setActivate_M(int activate_M) {
        this.activate_M = activate_M;
    }

    public int getActivate_L() {
        return activate_L;
    }

    public void setActivate_L(int activate_L) {
        this.activate_L = activate_L;
    }

    public int getActivate_XL() {
        return activate_XL;
    }

    public void setActivate_XL(int activate_XL) {
        this.activate_XL = activate_XL;
    }

    public int getActivate_XXL() {
        return activate_XXL;
    }

    public void setActivate_XXL(int activate_XXL) {
        this.activate_XXL = activate_XXL;
    }

    public int getActivate_Plus_0XL() {
        return activate_Plus_0XL;
    }

    public void setActivate_Plus_0XL(int activate_Plus_0XL) {
        this.activate_Plus_0XL = activate_Plus_0XL;
    }

    public int getActivate_Plus_1XL() {
        return activate_Plus_1XL;
    }

    public void setActivate_Plus_1XL(int activate_Plus_1XL) {
        this.activate_Plus_1XL = activate_Plus_1XL;
    }

    public int getActivate_Plus_2XL() {
        return activate_Plus_2XL;
    }

    public void setActivate_Plus_2XL(int activate_Plus_2XL) {
        this.activate_Plus_2XL = activate_Plus_2XL;
    }

    public int getActivate_Plus_3XL() {
        return activate_Plus_3XL;
    }

    public void setActivate_Plus_3XL(int activate_Plus_3XL) {
        this.activate_Plus_3XL = activate_Plus_3XL;
    }

    public int getActivate_Plus_4XL() {
        return activate_Plus_4XL;
    }

    public void setActivate_Plus_4XL(int activate_Plus_4XL) {
        this.activate_Plus_4XL = activate_Plus_4XL;
    }

    public int getActivate_Plus_5XL() {
        return activate_Plus_5XL;
    }

    public void setActivate_Plus_5XL(int activate_Plus_5XL) {
        this.activate_Plus_5XL = activate_Plus_5XL;
    }

    public int getActivate_Plus_6XL() {
        return activate_Plus_6XL;
    }

    public void setActivate_Plus_6XL(int activate_Plus_6XL) {
        this.activate_Plus_6XL = activate_Plus_6XL;
    }


}

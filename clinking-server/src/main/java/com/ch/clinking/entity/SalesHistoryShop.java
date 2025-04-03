package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("`sales_history_shop`")
public class SalesHistoryShop implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("shopId")
    private String shopId;

    @TableField("shopName")
    private String shopName;

    @TableField("salesNum")
    private Integer salesNum=0;

    @TableField("salesProfits")
    private double salesProfits=0;

    @TableField("totalProductNum")
    private Integer totalProductNum=0;

    @TableField("totalCost")
    private double totalCost=0;

    @TableField("superAdminAccount")
    private String superAdminAccount="";

    @TableField("createdTime")
    private Date createdTime;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public double getSalesProfits() {
        return salesProfits;
    }

    public void setSalesProfits(double salesProfits) {
        this.salesProfits = salesProfits;
    }

    public Integer getTotalProductNum() {
        return totalProductNum;
    }

    public void setTotalProductNum(Integer totalProductNum) {
        this.totalProductNum = totalProductNum;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getSuperAdminAccount() {
        return superAdminAccount;
    }

    public void setSuperAdminAccount(String superAdminAccount) {
        this.superAdminAccount = superAdminAccount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}

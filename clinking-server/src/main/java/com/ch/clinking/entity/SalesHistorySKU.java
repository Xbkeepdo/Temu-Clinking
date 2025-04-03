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
@TableName("`sales_history_sku`")
public class SalesHistorySKU implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("skcId")
    private String skcId;

    @TableField("skuId")
    private String skuId;

    @TableField("className")
    private String className;

    @TableField("skuSizeNum")
    private Integer skuSizeNum;

    @TableField("salesDate")
    private Date salesDate;

    @TableField("salesCount")
    private Integer salesCount = 0;

    @TableField("createdTime")
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getSkuSizeNum() {
        return skuSizeNum;
    }

    public void setSkuSizeNum(Integer skuSizeNum) {
        this.skuSizeNum = skuSizeNum;
    }
}

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
@TableName("`sales_history`")
public class SalesHistory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("skcId")
    private String skcId;

    @TableField("salesDate")
    private Date salesDate;

    @TableField("salesCount")
    private Integer salesCount;

    @TableField("createdTime")
    private Date createdTime;

    @TableField("totalSaleVolume")
    private Integer totalSaleVolume;

    @TableField("inCartNumber7d")
    private Integer inCartNumber7d;

    @TableField("inCardNumber")
    private Integer inCardNumber;

    @TableField("onSalesDurationOffline")
    private Integer onSalesDurationOffline;

    public Integer getOnSalesDurationOffline() {
        return onSalesDurationOffline;
    }

    public void setOnSalesDurationOffline(Integer onSalesDurationOffline) {
        this.onSalesDurationOffline = onSalesDurationOffline;
    }

    public Integer getTotalSaleVolume() {
        return totalSaleVolume;
    }

    public void setTotalSaleVolume(Integer totalSaleVolume) {
        this.totalSaleVolume = totalSaleVolume;
    }

    public Integer getInCartNumber7d() {
        return inCartNumber7d;
    }

    public void setInCartNumber7d(Integer inCartNumber7d) {
        this.inCartNumber7d = inCartNumber7d;
    }

    public Integer getInCardNumber() {
        return inCardNumber;
    }

    public void setInCardNumber(Integer inCardNumber) {
        this.inCardNumber = inCardNumber;
    }

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
}

package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("sale_info")
public class SaleInfo implements Serializable {

//    @TableId(type = IdType.AUTO)
//    private Integer id;

    @TableField("skuId")
    @TableId
    private String skuId;

    @TableField("skcId")
    private String skcId;

    @TableField("className")
    private String className;

    @TableField("skuSizeNum")
    private Integer skuSizeNum;

    @TableField("onSalesDurationOffline")
    private Integer onSalesDurationOffline;

    @TableField("todaySaleVolume")
    private Integer todaySaleVolume;

    @TableField("lastSevenDaysSaleVolume")
    private Integer lastSevenDaysSaleVolume;

    @TableField("lastThirtyDaysSaleVolume")
    private Integer lastThirtyDaysSaleVolume;

    @TableField("warehouseInventoryNum")
    private Integer warehouseInventoryNum;

    @TableField("waitOnShelfNum")
    private Integer waitOnShelfNum;

    @TableField("waitReceiveNum")
    private Integer waitReceiveNum;

    @TableField("remark")
    private String remark;

    @TableField("mark")
    private double mark;

    @TableField("totalSaleVolume")
    private Integer totalSaleVolume;

    @TableField("inCartNumber7d")
    private Integer inCartNumber7d;

    @TableField("inCardNumber")
    private Integer inCardNumber;

    @TableField("hotTag")
    private boolean hotTag;

    @TableField("hasHotSku")
    private boolean hasHotSku;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
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

    public Integer getOnSalesDurationOffline() {
        return onSalesDurationOffline;
    }

    public void setOnSalesDurationOffline(Integer onSalesDurationOffline) {
        this.onSalesDurationOffline = onSalesDurationOffline;
    }

    public Integer getTodaySaleVolume() {
        return todaySaleVolume;
    }

    public void setTodaySaleVolume(Integer todaySaleVolume) {
        this.todaySaleVolume = todaySaleVolume;
    }

    public Integer getLastSevenDaysSaleVolume() {
        return lastSevenDaysSaleVolume;
    }

    public void setLastSevenDaysSaleVolume(Integer lastSevenDaysSaleVolume) {
        this.lastSevenDaysSaleVolume = lastSevenDaysSaleVolume;
    }

    public Integer getLastThirtyDaysSaleVolume() {
        return lastThirtyDaysSaleVolume;
    }

    public void setLastThirtyDaysSaleVolume(Integer lastThirtyDaysSaleVolume) {
        this.lastThirtyDaysSaleVolume = lastThirtyDaysSaleVolume;
    }

    public Integer getWarehouseInventoryNum() {
        return warehouseInventoryNum;
    }

    public void setWarehouseInventoryNum(Integer warehouseInventoryNum) {
        this.warehouseInventoryNum = warehouseInventoryNum;
    }

    public Integer getWaitOnShelfNum() {
        return waitOnShelfNum;
    }

    public void setWaitOnShelfNum(Integer waitOnShelfNum) {
        this.waitOnShelfNum = waitOnShelfNum;
    }

    public Integer getWaitReceiveNum() {
        return waitReceiveNum;
    }

    public void setWaitReceiveNum(Integer waitReceiveNum) {
        this.waitReceiveNum = waitReceiveNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
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

    public boolean isHotTag() {
        return hotTag;
    }

    public void setHotTag(boolean hotTag) {
        this.hotTag = hotTag;
    }

    public boolean isHasHotSku() {
        return hasHotSku;
    }

    public void setHasHotSku(boolean hasHotSku) {
        this.hasHotSku = hasHotSku;
    }
}

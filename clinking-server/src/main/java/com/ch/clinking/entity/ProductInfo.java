package com.ch.clinking.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("productInfo")
public class ProductInfo {
    @TableId
    private String skcId;
    private String spuId;
    private Integer productionPhase;
    private String material;
    private String sizeInfo;
    private String clothInfo;
    private String technologicalRequirements;
    private String fuLiaoInfo;
    private String remark;
    private String fabricSampleWeight;
    private String detailImg;
    private String sampleWeight;

    @TableField(exist = false)
    private SizeTable sizeTable;

    @TableField(exist = false)
    private List<FabricDetail> fabricDetails;

    @TableField(exist = false)
    private List<Accessory> accessories;

    @Data
    public static class SizeTable {
        private List<String> columns;
        private List<List<String>> data;
    }

    @Data
    public static class FabricDetail {
        private String name;
        private String color;
        private String spec;
        private String supplier;
    }

    @Data
    public static class Accessory {
        private String name;
        private String supplier;
    }

    @PostLoad
    private void deserializeComplexFields() {
        if (StringUtils.hasText(this.sizeInfo)) {
            this.sizeTable = JSON.parseObject(this.sizeInfo, SizeTable.class);
        }
        if (StringUtils.hasText(this.clothInfo)) {
            this.fabricDetails = JSON.parseArray(this.clothInfo, FabricDetail.class);
        }
        if (StringUtils.hasText(this.fuLiaoInfo)) {
            this.accessories = JSON.parseArray(this.fuLiaoInfo, Accessory.class);
        }
    }

    @PrePersist
    @PreUpdate
    private void serializeComplexFields() {
        if (this.sizeTable != null) {
            this.sizeInfo = JSON.toJSONString(this.sizeTable);
        }
        if (this.fabricDetails != null) {
            this.clothInfo = JSON.toJSONString(this.fabricDetails);
        }
        if (this.accessories != null) {
            this.fuLiaoInfo = JSON.toJSONString(this.accessories);
        }
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public Integer getProductionPhase() {
        return productionPhase;
    }

    public void setProductionPhase(Integer productionPhase) {
        this.productionPhase = productionPhase;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public String getClothInfo() {
        return clothInfo;
    }

    public void setClothInfo(String clothInfo) {
        this.clothInfo = clothInfo;
    }

    public String getTechnologicalRequirements() {
        return technologicalRequirements;
    }

    public void setTechnologicalRequirements(String technologicalRequirements) {
        this.technologicalRequirements = technologicalRequirements;
    }

    public String getFuLiaoInfo() {
        return fuLiaoInfo;
    }

    public void setFuLiaoInfo(String fuLiaoInfo) {
        this.fuLiaoInfo = fuLiaoInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFabricSampleWeight() {
        return fabricSampleWeight;
    }

    public void setFabricSampleWeight(String fabricSampleWeight) {
        this.fabricSampleWeight = fabricSampleWeight;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public SizeTable getSizeTable() {
        return sizeTable;
    }

    public void setSizeTable(SizeTable sizeTable) {
        this.sizeTable = sizeTable;
    }

    public List<FabricDetail> getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(List<FabricDetail> fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }
}
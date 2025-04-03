package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("`production_info`")
public class ProductionInfo implements Serializable{

    @TableId
    private String skcId;

    private String spuId;

    private int productionPhase;

    private String material;

    private String clothInfo;

    private String technologicalRequirements;

    private String fuLiaoInfo;

    private String remark;

    private String fabricSampleWeight;

    private String detailImg = "default.png";

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public String getFabricSampleWeight() {
        return fabricSampleWeight;
    }

    public void setFabricSampleWeight(String fabricSampleWeight) {
        this.fabricSampleWeight = fabricSampleWeight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFuLiaoInfo() {
        return fuLiaoInfo;
    }

    public void setFuLiaoInfo(String fuLiaoInfo) {
        this.fuLiaoInfo = fuLiaoInfo;
    }

    public String getTechnologicalRequirements() {
        return technologicalRequirements;
    }

    public void setTechnologicalRequirements(String technologicalRequirements) {
        this.technologicalRequirements = technologicalRequirements;
    }

    public String getClothInfo() {
        return clothInfo;
    }

    public void setClothInfo(String clothInfo) {
        this.clothInfo = clothInfo;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public int getProductionPhase() {
        return productionPhase;
    }

    public void setProductionPhase(int productionPhase) {
        this.productionPhase = productionPhase;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}

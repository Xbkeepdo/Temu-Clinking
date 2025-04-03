package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("`cost`")
public class Cost implements Serializable {

    @TableId
    private String skcId;

    private String spuId;

    private String shopId;

    private double cloth = 0;

    private double cutInto = 0;

    private double tailor = 0;

    private double printing = 0;

    private double pack = 0;

    private double total = 0;

    private double qualityRefundRate = 0;

    private double sizeRefundRate = 0;

    private double more = 0;

    private double qualityScore = 0;

    private double freight = 0; // 袋子运费

    private double button = 0;

    private double ribbing = 0; // 罗纹

    private double rubber = 0; // 橡筋

    private double string = 0; // 绳子

    private double sack = 0; // 袋子



    private double airHole = 0; // 气眼 1
    private double zipper = 0; // 拉链 1
    private double chain = 0; // 链条 1
    private double beltloop = 0; // 耳仔 1
    private double paper = 0; // 衬纸 1
    private double cloth2 = 0; // 其他面料 1
    private double lockHole = 0; // 锁眼 1

    private double otherCraft = 0; // 其他工艺 1
    private double sewButton = 0; // 钉扣子 1

    private String otherFiliaoStr = "";

    /*
    气眼	6
    半圆	2
    圆圈	2
    扣子	2
    网布	0.6
    */


    public Cost(String spuId, String skcId, String shopId) {
        this.spuId = spuId;
        this.skcId = skcId;
        this.shopId = shopId;
    }

    public Cost() {

    }

    public String getOtherFiliaoStr() {
        return otherFiliaoStr;
    }

    public void setOtherFiliaoStr(String otherFiliaoStr) {
        this.otherFiliaoStr = otherFiliaoStr;
    }

    public double getSewButton() {
        return sewButton;
    }

    public void setSewButton(double sewButton) {
        this.sewButton = sewButton;
    }

    public double getString() {
        return string;
    }

    public void setString(double string) {
        this.string = string;
    }

    public double getSack() {
        return sack;
    }

    public void setSack(double sack) {
        this.sack = sack;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getButton() {
        return button;
    }

    public void setButton(double button) {
        this.button = button;
    }

    public double getRibbing() {
        return ribbing;
    }

    public void setRibbing(double ribbing) {
        this.ribbing = ribbing;
    }

    public double getRubber() {
        return rubber;
    }

    public void setRubber(double rubber) {
        this.rubber = rubber;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getCloth() {
        return cloth;
    }

    public void setCloth(double cloth) {
        this.cloth = cloth;
    }

    public double getCutInto() {
        return cutInto;
    }

    public void setCutInto(double cutInto) {
        this.cutInto = cutInto;
    }

    public double getTailor() {
        return tailor;
    }

    public void setTailor(double tailor) {
        this.tailor = tailor;
    }

    public double getPrinting() {
        return printing;
    }

    public void setPrinting(double printing) {
        this.printing = printing;
    }

    public double getPack() {
        return pack;
    }

    public void setPack(double pack) {
        this.pack = pack;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getQualityRefundRate() {
        return qualityRefundRate;
    }

    public void setQualityRefundRate(double qualityRefundRate) {
        this.qualityRefundRate = qualityRefundRate;
    }

    public double getSizeRefundRate() {
        return sizeRefundRate;
    }

    public void setSizeRefundRate(double sizeRefundRate) {
        this.sizeRefundRate = sizeRefundRate;
    }

    public double getMore() {
        return more;
    }

    public void setMore(double more) {
        this.more = more;
    }

    public double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public double getZipper() {
        return zipper;
    }

    public void setZipper(double zipper) {
        this.zipper = zipper;
    }

    public double getChain() {
        return chain;
    }

    public void setChain(double chain) {
        this.chain = chain;
    }

    public double getBeltloop() {
        return beltloop;
    }

    public void setBeltloop(double beltloop) {
        this.beltloop = beltloop;
    }

    public double getPaper() {
        return paper;
    }

    public void setPaper(double paper) {
        this.paper = paper;
    }

    public double getCloth2() {
        return cloth2;
    }

    public void setCloth2(double cloth2) {
        this.cloth2 = cloth2;
    }

    public double getLockHole() {
        return lockHole;
    }

    public void setLockHole(double lockHole) {
        this.lockHole = lockHole;
    }

    public double getOtherCraft() {
        return otherCraft;
    }

    public void setOtherCraft(double otherCraft) {
        this.otherCraft = otherCraft;
    }

    public double getAirHole() {
        return airHole;
    }

    public void setAirHole(double airHole) {
        this.airHole = airHole;
    }

}

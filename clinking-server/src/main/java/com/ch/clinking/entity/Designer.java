package com.ch.clinking.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("`designer`")
public class Designer implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String superAdminAccount;

    private String name;

    private String account;

    private int productType;

    private int numberOfProductOwned;  // 绑定款已上架款数

    private int successCount;  // 上架天数大于10天小于60天，且有3日平均周销量大于30件的记录

    private double successRate;  // = successCount / numberOfProductOwned

    private int averageReviewPatternCompletedTime; // 平均审版完成所花费时间      = 当产品第一次到达“平台核价中”时触发， = （averageReviewPatternCompletedTime（产品的属性） + averageReviewPatternCompletedTime） / 2

    private int averagePriceCheckTime;  // 平均核价所花费的平均时间        = 当产品待下首单时触发，(averagePriceCheckTime + priceCheckTime（核价所花费的时间）)/（waitForTheFirstOrderTimeProductCount + 1）

    private int waitForTheFirstOrderTimeProductCount; // 到达待下首单环节的产品数量      = waitForTheFirstOrderTime为null，且产品状态是“待下首单”时

    private int arrivedReviewPatternCount;  // 到达过审版环节的产品数，可用这个算平均审版次数，和一次审版通过率  = reviewPatternCompletedTime为null，且产品状态是“待平台审版”时

    private int oneReviewPatternPassCount; // 一次审版通过次数      = 当产品第一次到达“平台核价中”状态（startOfPriceCheckTime为null，且产品状态是“平台核价中”时） 并且 产品审版次数（reviewPatternCount）为1时，此数量加1

    private double oneReviewPatternPassRate; // 一次审版通过率     = oneReviewPatternPassCount/arrivedReviewPatternCount

    private int day30ProductSalesCount; // 30天绑定款售卖数量

//    private int todayProductSalesProfit; // 今日绑定款售卖利润
//
//    private int todayProductSalesRateOfProfit; // 今日绑定款售卖利润

    private int averageReturnRate; // 平均售后率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuperAdminAccount() {
        return superAdminAccount;
    }

    public void setSuperAdminAccount(String superAdminAccount) {
        this.superAdminAccount = superAdminAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getNumberOfProductOwned() {
        return numberOfProductOwned;
    }

    public void setNumberOfProductOwned(int numberOfProductOwned) {
        this.numberOfProductOwned = numberOfProductOwned;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public int getAverageReviewPatternCompletedTime() {
        return averageReviewPatternCompletedTime;
    }

    public void setAverageReviewPatternCompletedTime(int averageReviewPatternCompletedTime) {
        this.averageReviewPatternCompletedTime = averageReviewPatternCompletedTime;
    }

    public int getAveragePriceCheckTime() {
        return averagePriceCheckTime;
    }

    public void setAveragePriceCheckTime(int averagePriceCheckTime) {
        this.averagePriceCheckTime = averagePriceCheckTime;
    }

    public int getWaitForTheFirstOrderTimeProductCount() {
        return waitForTheFirstOrderTimeProductCount;
    }

    public void setWaitForTheFirstOrderTimeProductCount(int waitForTheFirstOrderTimeProductCount) {
        this.waitForTheFirstOrderTimeProductCount = waitForTheFirstOrderTimeProductCount;
    }

    public int getArrivedReviewPatternCount() {
        return arrivedReviewPatternCount;
    }

    public void setArrivedReviewPatternCount(int arrivedReviewPatternCount) {
        this.arrivedReviewPatternCount = arrivedReviewPatternCount;
    }

    public int getOneReviewPatternPassCount() {
        return oneReviewPatternPassCount;
    }

    public void setOneReviewPatternPassCount(int oneReviewPatternPassCount) {
        this.oneReviewPatternPassCount = oneReviewPatternPassCount;
    }

    public double getOneReviewPatternPassRate() {
        return oneReviewPatternPassRate;
    }

    public void setOneReviewPatternPassRate(double oneReviewPatternPassRate) {
        this.oneReviewPatternPassRate = oneReviewPatternPassRate;
    }

    public int getDay30ProductSalesCount() {
        return day30ProductSalesCount;
    }

    public void setDay30ProductSalesCount(int day30ProductSalesCount) {
        this.day30ProductSalesCount = day30ProductSalesCount;
    }

    public int getAverageReturnRate() {
        return averageReturnRate;
    }

    public void setAverageReturnRate(int averageReturnRate) {
        this.averageReturnRate = averageReturnRate;
    }

    @Override
    public String toString() {
        return "Designer{" +
                "id=" + id +
                ", superAdminAccount='" + superAdminAccount + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", productType=" + productType +
                ", numberOfProductOwned=" + numberOfProductOwned +
                ", successCount=" + successCount +
                ", successRate=" + successRate +
                ", averageReviewPatternCompletedTime=" + averageReviewPatternCompletedTime +
                ", averagePriceCheckTime=" + averagePriceCheckTime +
                ", waitForTheFirstOrderTimeProductCount=" + waitForTheFirstOrderTimeProductCount +
                ", arrivedReviewPatternCount=" + arrivedReviewPatternCount +
                ", oneReviewPatternPassCount=" + oneReviewPatternPassCount +
                ", oneReviewPatternPassRate=" + oneReviewPatternPassRate +
                ", day30ProductSalesCount=" + day30ProductSalesCount +
                ", averageReturnRate=" + averageReturnRate +
                '}';
    }
}

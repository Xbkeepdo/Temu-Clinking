package com.ch.clinking.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.Designer;
import com.ch.clinking.entity.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;


@Data

@Accessors(chain = true)
public class ProductVO  {

    private Integer id;

    private String shopId;

    private String spuId;

    private String skcId;

    private String sSku;

    private String title;

    private String productNumber = "";  // 初始化时未赋值

    private String catName;
    private String firstImage;
    private double price;   // 初始化时未赋值
    private String state;   // 初始化时未赋值

    private String shopType;

    private Integer isActive; // 是否启用

    private Integer fabricSampleWeight; // 样布实际克重  // 初始化时未赋值

    private String skuList = "";  // sku列表
    //时间考核节点

    private long createTime;

    private long sampleDeliveryTime;  // 第一次、寄样中时间节点

    private long sampleReviewPatternTime;  // 第一次、“待平台审版”中时间节点

//    private long reviewPatternCompletedTime;  // 第一次"审版不合格"或"平台核价中",时间

    private long startOfPriceCheckTime;  // 第一次"平台核价中",时间

    private int reviewPatternCount=0;  // 审版次数

    private long waitForTheFirstOrderTime;  // 待下首单时间

    private int priceCheckTimeSpent;  // 核价所花费时间       = waitForTheFirstOrderTime - startOfPriceCheckTime

    private int reviewPatternCompletedTimeSpent;  // 审版完成所花费时间     = startOfPriceCheckTime - createTime

    private long averageReviewPatternCompletedTime;  // 平均审版完成所花费时间     = （startOfPriceCheckTime - createTime） / reviewPatternCount




    //拓展字段

    private List<String> designerList;

   // private Cost cost;

    private double salesCount;

    private BigDecimal dailySales;

    private double totalCost;//总成本



    // 以下为需要补充的字段（根据原Product类中的@TableField(exist=false)字段）






    //private List<Designer> designerList;


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


    public String getsSku() {
        return sSku;
    }


    public void setsSku(String sSku) {
        this.sSku = sSku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getFabricSampleWeight() {
        return fabricSampleWeight;
    }

    public void setFabricSampleWeight(Integer fabricSampleWeight) {
        this.fabricSampleWeight = fabricSampleWeight;
    }

    public String getSkuList() {
        return skuList;
    }

    public void setSkuList(String skuList) {
        this.skuList = skuList;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getSampleDeliveryTime() {
        return sampleDeliveryTime;
    }

    public void setSampleDeliveryTime(long sampleDeliveryTime) {
        this.sampleDeliveryTime = sampleDeliveryTime;
    }

    public long getSampleReviewPatternTime() {
        return sampleReviewPatternTime;
    }

    public void setSampleReviewPatternTime(long sampleReviewPatternTime) {
        this.sampleReviewPatternTime = sampleReviewPatternTime;
    }

    public long getStartOfPriceCheckTime() {
        return startOfPriceCheckTime;
    }

    public void setStartOfPriceCheckTime(long startOfPriceCheckTime) {
        this.startOfPriceCheckTime = startOfPriceCheckTime;
    }

    public int getReviewPatternCount() {
        return reviewPatternCount;
    }

    public void setReviewPatternCount(int reviewPatternCount) {
        this.reviewPatternCount = reviewPatternCount;
    }

    public long getWaitForTheFirstOrderTime() {
        return waitForTheFirstOrderTime;
    }

    public void setWaitForTheFirstOrderTime(long waitForTheFirstOrderTime) {
        this.waitForTheFirstOrderTime = waitForTheFirstOrderTime;
    }

    public int getPriceCheckTimeSpent() {
        return priceCheckTimeSpent;
    }

    public void setPriceCheckTimeSpent(int priceCheckTimeSpent) {
        this.priceCheckTimeSpent = priceCheckTimeSpent;
    }

    public int getReviewPatternCompletedTimeSpent() {
        return reviewPatternCompletedTimeSpent;
    }

    public void setReviewPatternCompletedTimeSpent(int reviewPatternCompletedTimeSpent) {
        this.reviewPatternCompletedTimeSpent = reviewPatternCompletedTimeSpent;
    }

    public long getAverageReviewPatternCompletedTime() {
        return averageReviewPatternCompletedTime;
    }

    public void setAverageReviewPatternCompletedTime(long averageReviewPatternCompletedTime) {
        this.averageReviewPatternCompletedTime = averageReviewPatternCompletedTime;
    }

//    public List<Designer> getDesignerList() {
//        return designerList;
//    }
//
//    public void setDesignerList(List<Designer> designerList) {
//        this.designerList = designerList;
//    }
//
//    public Cost getCost() {
//        return cost;
//    }
//
//    public void setCost(Cost cost) {
//        this.cost = cost;
//    }

    public double getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(double salesCount) {
        this.salesCount = salesCount;
    }

    public BigDecimal getDailySales() {
        return dailySales;
    }

    public void setDailySales(BigDecimal setDailySales) {
        this.dailySales = setDailySales;
    }

    public List<String> getDesignerList() {
        return designerList;
    }

    public void setDesignerList(List<String> designerList) {
        this.designerList = designerList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}


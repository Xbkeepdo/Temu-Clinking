package com.ch.clinking.entity.Dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.CustomDateTimeSerializer;
import com.ch.clinking.entity.Order;
import com.ch.clinking.entity.SaleInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Data
@ToString
public class MerSaleAndCostDTO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String shopId;

    private String spuId;

    private String skcId;

    private String productNumber="";

    private String title;

    private String firstImage = "default.png";

    private String color;

    private String state;

    private double price;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(select = false,exist = false)
    private List<Order> orderList;

    private String shopType;

    private String sizeInfo;

    private Integer weight;

    @TableField(select = false,exist = false)
    private List<SaleInfo> saleInfoList;

    @TableField(select = false,exist = false)
    private Cost cost;

    private int inHouseStock_XXS = 0;
    private int inHouseStock_XS = 0;
    private int inHouseStock_S = 0;
    private int inHouseStock_M = 0;
    private int inHouseStock_L = 0;
    private int inHouseStock_XL = 0;
    private int inHouseStock_XXL = 0;

    private int inHouseStock_Plus_0XL = 0;
    private int inHouseStock_Plus_1XL = 0;
    private int inHouseStock_Plus_2XL = 0;
    private int inHouseStock_Plus_3XL = 0;
    private int inHouseStock_Plus_4XL = 0;
    private int inHouseStock_Plus_5XL = 0;
    private int inHouseStock_Plus_6XL = 0;


    private int platformStock_XXS = 0;
    private int platformStock_XS = 0;
    private int platformStock_S = 0;
    private int platformStock_M = 0;
    private int platformStock_L = 0;
    private int platformStock_XL = 0;
    private int platformStock_XXL = 0;

    private int platformStock_Plus_0XL = 0;
    private int platformStock_Plus_1XL = 0;
    private int platformStock_Plus_2XL = 0;
    private int platformStock_Plus_3XL = 0;
    private int platformStock_Plus_4XL = 0;
    private int platformStock_Plus_5XL = 0;
    private int platformStock_Plus_6XL = 0;

    private boolean activate_XXS = false;
    private boolean activate_XS = false;
    private boolean activate_S = false;
    private boolean activate_M = false;
    private boolean activate_L = false;
    private boolean activate_XL = false;
    private boolean activate_XXL = false;

    private boolean activate_Plus_0XL = false;
    private boolean activate_Plus_1XL = false;
    private boolean activate_Plus_2XL = false;
    private boolean activate_Plus_3XL = false;
    private boolean activate_Plus_4XL = false;
    private boolean activate_Plus_5XL = false;
    private boolean activate_Plus_6XL = false;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public boolean isActivate_XXS() {
        return activate_XXS;
    }

    public void setActivate_XXS(boolean activate_XXS) {
        this.activate_XXS = activate_XXS;
    }

    public boolean isActivate_XS() {
        return activate_XS;
    }

    public void setActivate_XS(boolean activate_XS) {
        this.activate_XS = activate_XS;
    }

    public boolean isActivate_S() {
        return activate_S;
    }

    public void setActivate_S(boolean activate_S) {
        this.activate_S = activate_S;
    }

    public boolean isActivate_M() {
        return activate_M;
    }

    public void setActivate_M(boolean activate_M) {
        this.activate_M = activate_M;
    }

    public boolean isActivate_L() {
        return activate_L;
    }

    public void setActivate_L(boolean activate_L) {
        this.activate_L = activate_L;
    }

    public boolean isActivate_XL() {
        return activate_XL;
    }

    public void setActivate_XL(boolean activate_XL) {
        this.activate_XL = activate_XL;
    }

    public boolean isActivate_XXL() {
        return activate_XXL;
    }

    public void setActivate_XXL(boolean activate_XXL) {
        this.activate_XXL = activate_XXL;
    }

    public boolean isActivate_Plus_1XL() {
        return activate_Plus_1XL;
    }

    public int getInHouseStock_Plus_0XL() {
        return inHouseStock_Plus_0XL;
    }

    public void setInHouseStock_Plus_0XL(int inHouseStock_Plus_0XL) {
        this.inHouseStock_Plus_0XL = inHouseStock_Plus_0XL;
    }

    public int getPlatformStock_Plus_0XL() {
        return platformStock_Plus_0XL;
    }

    public void setPlatformStock_Plus_0XL(int platformStock_Plus_0XL) {
        this.platformStock_Plus_0XL = platformStock_Plus_0XL;
    }

    public boolean isActivate_Plus_0XL() {
        return activate_Plus_0XL;
    }

    public void setActivate_Plus_0XL(boolean activate_Plus_0XL) {
        this.activate_Plus_0XL = activate_Plus_0XL;
    }

    public void setActivate_Plus_1XL(boolean activate_Plus_1XL) {
        this.activate_Plus_1XL = activate_Plus_1XL;
    }

    public boolean isActivate_Plus_2XL() {
        return activate_Plus_2XL;
    }

    public void setActivate_Plus_2XL(boolean activate_Plus_2XL) {
        this.activate_Plus_2XL = activate_Plus_2XL;
    }

    public boolean isActivate_Plus_3XL() {
        return activate_Plus_3XL;
    }

    public void setActivate_Plus_3XL(boolean activate_Plus_3XL) {
        this.activate_Plus_3XL = activate_Plus_3XL;
    }

    public boolean isActivate_Plus_4XL() {
        return activate_Plus_4XL;
    }

    public void setActivate_Plus_4XL(boolean activate_Plus_4XL) {
        this.activate_Plus_4XL = activate_Plus_4XL;
    }

    public boolean isActivate_Plus_5XL() {
        return activate_Plus_5XL;
    }

    public void setActivate_Plus_5XL(boolean activate_Plus_5XL) {
        this.activate_Plus_5XL = activate_Plus_5XL;
    }

    public boolean isActivate_Plus_6XL() {
        return activate_Plus_6XL;
    }

    public void setActivate_Plus_6XL(boolean activate_Plus_6XL) {
        this.activate_Plus_6XL = activate_Plus_6XL;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public int getInHouseStock_XXS() {
        return inHouseStock_XXS;
    }

    public void setInHouseStock_XXS(int inHouseStock_XXS) {
        this.inHouseStock_XXS = inHouseStock_XXS;
    }

    public int getInHouseStock_XS() {
        return inHouseStock_XS;
    }

    public void setInHouseStock_XS(int inHouseStock_XS) {
        this.inHouseStock_XS = inHouseStock_XS;
    }

    public int getInHouseStock_S() {
        return inHouseStock_S;
    }

    public void setInHouseStock_S(int inHouseStock_S) {
        this.inHouseStock_S = inHouseStock_S;
    }

    public int getInHouseStock_M() {
        return inHouseStock_M;
    }

    public void setInHouseStock_M(int inHouseStock_M) {
        this.inHouseStock_M = inHouseStock_M;
    }

    public int getInHouseStock_L() {
        return inHouseStock_L;
    }

    public void setInHouseStock_L(int inHouseStock_L) {
        this.inHouseStock_L = inHouseStock_L;
    }

    public int getInHouseStock_XL() {
        return inHouseStock_XL;
    }

    public void setInHouseStock_XL(int inHouseStock_XL) {
        this.inHouseStock_XL = inHouseStock_XL;
    }

    public int getInHouseStock_XXL() {
        return inHouseStock_XXL;
    }

    public void setInHouseStock_XXL(int inHouseStock_XXL) {
        this.inHouseStock_XXL = inHouseStock_XXL;
    }

    public int getInHouseStock_Plus_1XL() {
        return inHouseStock_Plus_1XL;
    }

    public void setInHouseStock_Plus_1XL(int inHouseStock_Plus_1XL) {
        this.inHouseStock_Plus_1XL = inHouseStock_Plus_1XL;
    }

    public int getInHouseStock_Plus_2XL() {
        return inHouseStock_Plus_2XL;
    }

    public void setInHouseStock_Plus_2XL(int inHouseStock_Plus_2XL) {
        this.inHouseStock_Plus_2XL = inHouseStock_Plus_2XL;
    }

    public int getInHouseStock_Plus_3XL() {
        return inHouseStock_Plus_3XL;
    }

    public void setInHouseStock_Plus_3XL(int inHouseStock_Plus_3XL) {
        this.inHouseStock_Plus_3XL = inHouseStock_Plus_3XL;
    }

    public int getInHouseStock_Plus_4XL() {
        return inHouseStock_Plus_4XL;
    }

    public void setInHouseStock_Plus_4XL(int inHouseStock_Plus_4XL) {
        this.inHouseStock_Plus_4XL = inHouseStock_Plus_4XL;
    }

    public int getInHouseStock_Plus_5XL() {
        return inHouseStock_Plus_5XL;
    }

    public void setInHouseStock_Plus_5XL(int inHouseStock_Plus_5XL) {
        this.inHouseStock_Plus_5XL = inHouseStock_Plus_5XL;
    }

    public int getInHouseStock_Plus_6XL() {
        return inHouseStock_Plus_6XL;
    }

    public void setInHouseStock_Plus_6XL(int inHouseStock_Plus_6XL) {
        this.inHouseStock_Plus_6XL = inHouseStock_Plus_6XL;
    }

    public int getPlatformStock_XXS() {
        return platformStock_XXS;
    }

    public void setPlatformStock_XXS(int platformStock_XXS) {
        this.platformStock_XXS = platformStock_XXS;
    }

    public int getPlatformStock_XS() {
        return platformStock_XS;
    }

    public void setPlatformStock_XS(int platformStock_XS) {
        this.platformStock_XS = platformStock_XS;
    }

    public int getPlatformStock_S() {
        return platformStock_S;
    }

    public void setPlatformStock_S(int platformStock_S) {
        this.platformStock_S = platformStock_S;
    }

    public int getPlatformStock_M() {
        return platformStock_M;
    }

    public void setPlatformStock_M(int platformStock_M) {
        this.platformStock_M = platformStock_M;
    }

    public int getPlatformStock_L() {
        return platformStock_L;
    }

    public void setPlatformStock_L(int platformStock_L) {
        this.platformStock_L = platformStock_L;
    }

    public int getPlatformStock_XL() {
        return platformStock_XL;
    }

    public void setPlatformStock_XL(int platformStock_XL) {
        this.platformStock_XL = platformStock_XL;
    }

    public int getPlatformStock_XXL() {
        return platformStock_XXL;
    }

    public void setPlatformStock_XXL(int platformStock_XXL) {
        this.platformStock_XXL = platformStock_XXL;
    }

    public int getPlatformStock_Plus_1XL() {
        return platformStock_Plus_1XL;
    }

    public void setPlatformStock_Plus_1XL(int platformStock_Plus_1XL) {
        this.platformStock_Plus_1XL = platformStock_Plus_1XL;
    }

    public int getPlatformStock_Plus_2XL() {
        return platformStock_Plus_2XL;
    }

    public void setPlatformStock_Plus_2XL(int platformStock_Plus_2XL) {
        this.platformStock_Plus_2XL = platformStock_Plus_2XL;
    }

    public int getPlatformStock_Plus_3XL() {
        return platformStock_Plus_3XL;
    }

    public void setPlatformStock_Plus_3XL(int platformStock_Plus_3XL) {
        this.platformStock_Plus_3XL = platformStock_Plus_3XL;
    }

    public int getPlatformStock_Plus_4XL() {
        return platformStock_Plus_4XL;
    }

    public void setPlatformStock_Plus_4XL(int platformStock_Plus_4XL) {
        this.platformStock_Plus_4XL = platformStock_Plus_4XL;
    }

    public int getPlatformStock_Plus_5XL() {
        return platformStock_Plus_5XL;
    }

    public void setPlatformStock_Plus_5XL(int platformStock_Plus_5XL) {
        this.platformStock_Plus_5XL = platformStock_Plus_5XL;
    }

    public int getPlatformStock_Plus_6XL() {
        return platformStock_Plus_6XL;
    }

    public void setPlatformStock_Plus_6XL(int platformStock_Plus_6XL) {
        this.platformStock_Plus_6XL = platformStock_Plus_6XL;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public List<SaleInfo> getSaleInfoList() {
        return saleInfoList;
    }

    public void setSaleInfoList(List<SaleInfo> saleInfoList) {
        this.saleInfoList = saleInfoList;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "MerSaleAndCostDTO{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", spuId='" + spuId + '\'' +
                ", skcId='" + skcId + '\'' +
                ", productNumber='" + productNumber + '\'' +
                ", title='" + title + '\'' +
                ", firstImage='" + firstImage + '\'' +
                ", color='" + color + '\'' +
                ", state='" + state + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                ", orderList=" + orderList +
                ", shopType='" + shopType + '\'' +
                ", sizeInfo='" + sizeInfo + '\'' +
                ", weight=" + weight +
                ", saleInfoList=" + saleInfoList +
                ", cost=" + cost +
                ", inHouseStock_XXS=" + inHouseStock_XXS +
                ", inHouseStock_XS=" + inHouseStock_XS +
                ", inHouseStock_S=" + inHouseStock_S +
                ", inHouseStock_M=" + inHouseStock_M +
                ", inHouseStock_L=" + inHouseStock_L +
                ", inHouseStock_XL=" + inHouseStock_XL +
                ", inHouseStock_XXL=" + inHouseStock_XXL +
                ", inHouseStock_Plus_0XL=" + inHouseStock_Plus_0XL +
                ", inHouseStock_Plus_1XL=" + inHouseStock_Plus_1XL +
                ", inHouseStock_Plus_2XL=" + inHouseStock_Plus_2XL +
                ", inHouseStock_Plus_3XL=" + inHouseStock_Plus_3XL +
                ", inHouseStock_Plus_4XL=" + inHouseStock_Plus_4XL +
                ", inHouseStock_Plus_5XL=" + inHouseStock_Plus_5XL +
                ", inHouseStock_Plus_6XL=" + inHouseStock_Plus_6XL +
                ", platformStock_XXS=" + platformStock_XXS +
                ", platformStock_XS=" + platformStock_XS +
                ", platformStock_S=" + platformStock_S +
                ", platformStock_M=" + platformStock_M +
                ", platformStock_L=" + platformStock_L +
                ", platformStock_XL=" + platformStock_XL +
                ", platformStock_XXL=" + platformStock_XXL +
                ", platformStock_Plus_0XL=" + platformStock_Plus_0XL +
                ", platformStock_Plus_1XL=" + platformStock_Plus_1XL +
                ", platformStock_Plus_2XL=" + platformStock_Plus_2XL +
                ", platformStock_Plus_3XL=" + platformStock_Plus_3XL +
                ", platformStock_Plus_4XL=" + platformStock_Plus_4XL +
                ", platformStock_Plus_5XL=" + platformStock_Plus_5XL +
                ", platformStock_Plus_6XL=" + platformStock_Plus_6XL +
                ", activate_XXS=" + activate_XXS +
                ", activate_XS=" + activate_XS +
                ", activate_S=" + activate_S +
                ", activate_M=" + activate_M +
                ", activate_L=" + activate_L +
                ", activate_XL=" + activate_XL +
                ", activate_XXL=" + activate_XXL +
                ", activate_Plus_0XL=" + activate_Plus_0XL +
                ", activate_Plus_1XL=" + activate_Plus_1XL +
                ", activate_Plus_2XL=" + activate_Plus_2XL +
                ", activate_Plus_3XL=" + activate_Plus_3XL +
                ", activate_Plus_4XL=" + activate_Plus_4XL +
                ", activate_Plus_5XL=" + activate_Plus_5XL +
                ", activate_Plus_6XL=" + activate_Plus_6XL +
                '}';
    }

}

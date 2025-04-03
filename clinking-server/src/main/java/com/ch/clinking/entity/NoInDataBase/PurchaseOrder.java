package com.ch.clinking.entity.NoInDataBase;

import java.util.Date;

public class PurchaseOrder {

    private String productSkcPicture;
    private String originalPurchaseOrderSn;
    private String subPurchaseOrderSn;
    private String productName;
    private String skcId;
    private boolean isFirst;
    private int urgencyType;
    private int purchaseQuantity;
    private boolean isCanJoinDeliverPlatform;
    private int urgencyLevel;  // 2晚于当前，1当天，0已超期
    private Date expectLatestDeliverTime;

    public String getSubPurchaseOrderSn() {
        return subPurchaseOrderSn;
    }

    public void setSubPurchaseOrderSn(String subPurchaseOrderSn) {
        this.subPurchaseOrderSn = subPurchaseOrderSn;
    }

    public String getOriginalPurchaseOrderSn() {
        return originalPurchaseOrderSn;
    }

    public void setOriginalPurchaseOrderSn(String originalPurchaseOrderSn) {
        this.originalPurchaseOrderSn = originalPurchaseOrderSn;
    }

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Date getExpectLatestDeliverTime() {
        return expectLatestDeliverTime;
    }

    public void setExpectLatestDeliverTime(Date expectLatestDeliverTime) {
        this.expectLatestDeliverTime = expectLatestDeliverTime;
    }

    public String getProductSkcPicture() {
        return productSkcPicture;
    }

    public void setProductSkcPicture(String productSkcPicture) {
        this.productSkcPicture = productSkcPicture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getUrgencyType() {
        return urgencyType;
    }

    public void setUrgencyType(int urgencyType) {
        this.urgencyType = urgencyType;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public boolean isCanJoinDeliverPlatform() {
        return isCanJoinDeliverPlatform;
    }

    public void setCanJoinDeliverPlatform(boolean canJoinDeliverPlatform) {
        isCanJoinDeliverPlatform = canJoinDeliverPlatform;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "productSkcPicture='" + productSkcPicture + '\'' +
                ", originalPurchaseOrderSn='" + originalPurchaseOrderSn + '\'' +
                ", subPurchaseOrderSn='" + subPurchaseOrderSn + '\'' +
                ", productName='" + productName + '\'' +
                ", skcId='" + skcId + '\'' +
                ", isFirst=" + isFirst +
                ", urgencyType=" + urgencyType +
                ", purchaseQuantity=" + purchaseQuantity +
                ", isCanJoinDeliverPlatform=" + isCanJoinDeliverPlatform +
                ", urgencyLevel=" + urgencyLevel +
                ", expectLatestDeliverTime=" + expectLatestDeliverTime +
                '}';
    }
}

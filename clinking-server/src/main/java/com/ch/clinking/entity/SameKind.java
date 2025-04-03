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
@TableName("same_kind")
public class SameKind implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("skcId")
    private String skcId;

    //用户名
    @TableField("mainSkcId")
    private String mainSkcId;

    @TableField("shopId")
    private String shopId;

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

    public String getMainSkcId() {
        return mainSkcId;
    }

    public void setMainSkcId(String mainSkcId) {
        this.mainSkcId = mainSkcId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}

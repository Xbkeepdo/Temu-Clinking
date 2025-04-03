package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Shop;

import java.util.List;

public interface ShopService extends IService<Shop> {

    Shop selectByShopId(String shopId);

    void updateBillCount(int billCount,String shopId);

    void updateShopName(String shopName,String shopId);

    List<Shop> selectByAccount(String account);
}

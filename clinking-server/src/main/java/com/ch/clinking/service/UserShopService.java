package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.User;
import com.ch.clinking.entity.UserShop;

import java.util.List;

public interface UserShopService extends IService<UserShop> {
    List<Shop> selectByAccount(String account);
}

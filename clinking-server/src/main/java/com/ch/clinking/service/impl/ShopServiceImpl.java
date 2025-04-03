package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.mapper.ShopMapper;
import com.ch.clinking.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Override
    public Shop selectByShopId(String shopId) {
        return baseMapper.selectByShopId(shopId);
    }

    @Override
    public void updateBillCount(int billCount, String shopId) {
        baseMapper.updateBillCount(billCount, shopId);
    }

    @Override
    public void updateShopName(String shopName, String shopId) {
        baseMapper.updateShopName(shopName, shopId);
    }

    @Override
    public List<Shop> selectByAccount(String account) {
        return baseMapper.selectByAccount(account);
    }
}

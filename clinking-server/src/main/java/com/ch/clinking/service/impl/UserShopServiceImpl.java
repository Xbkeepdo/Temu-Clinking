package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.UserShop;
import com.ch.clinking.mapper.UserShopMapper;
import com.ch.clinking.service.UserShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userShopService")
public class UserShopServiceImpl extends ServiceImpl<UserShopMapper, UserShop> implements UserShopService {

    @Resource
    private UserShopMapper userShopMapper;

    @Override
    public List<Shop> selectByAccount(String account) {
        return baseMapper.selectByAccount(account);
    }

}

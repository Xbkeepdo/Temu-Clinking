package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.StockChanged;
import com.ch.clinking.entity.UserShop;
import com.ch.clinking.mapper.StockChangedMapper;
import com.ch.clinking.mapper.UserShopMapper;
import com.ch.clinking.service.StockChangedService;
import com.ch.clinking.service.UserShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("stockChangedService")
public class StockChangedServiceImpl extends ServiceImpl<StockChangedMapper, StockChanged> implements StockChangedService {


}

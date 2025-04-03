package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.StockLocations;
import com.ch.clinking.mapper.ShopMapper;
import com.ch.clinking.mapper.StockLocationsMapper;
import com.ch.clinking.service.ShopService;
import com.ch.clinking.service.StockLocationsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("stockLocationsService")
public class StockLocationsServiceImpl extends ServiceImpl<StockLocationsMapper, StockLocations> implements StockLocationsService {

}

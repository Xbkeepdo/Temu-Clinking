package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.ProductionInfo;
import com.ch.clinking.mapper.CostMapper;
import com.ch.clinking.mapper.ProductionInfoMapper;
import com.ch.clinking.service.CostService;
import com.ch.clinking.service.ProductionInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productionInfoService")
public class ProductionInfoServiceImpl extends ServiceImpl<ProductionInfoMapper, ProductionInfo> implements ProductionInfoService {

    @Resource
    private ProductionInfoMapper productionInfoMapper;



}

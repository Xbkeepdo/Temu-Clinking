package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.ProductCost;
import com.ch.clinking.mapper.ProductCostMapper;
import com.ch.clinking.service.ProductCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productCostService")
public class ProductCostServiceImpl extends ServiceImpl<ProductCostMapper, ProductCost> implements ProductCostService {

    @Resource
    ProductCostMapper productCostMapper;

    @Override
    public ProductCost getProductCostBySkcId(String skcId) {
        return productCostMapper.selectOne(new QueryWrapper<ProductCost>().eq("skcId", skcId));
    }
}

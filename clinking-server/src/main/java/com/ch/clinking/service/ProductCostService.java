package com.ch.clinking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.ProductCost;
import com.ch.clinking.entity.ProductDesigner;
import org.springframework.stereotype.Service;

@Service
public interface ProductCostService extends IService<ProductCost> {

    public ProductCost getProductCostBySkcId(String skcId);
}

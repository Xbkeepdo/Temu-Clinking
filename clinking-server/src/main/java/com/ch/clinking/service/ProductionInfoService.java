package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.ProductionInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional  // 使用事务管理
@Service
public interface ProductionInfoService extends IService<ProductionInfo> {



    //获取店铺商品信息



}

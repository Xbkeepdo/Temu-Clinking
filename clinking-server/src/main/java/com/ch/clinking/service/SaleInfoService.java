package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Dto.MerSaleDTO;
import com.ch.clinking.entity.SaleInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional  // 使用事务管理
public interface SaleInfoService extends IService<SaleInfo> {


    Map<String, String> autoMakeOrderOneSkc(MerSaleDTO merSaleDTO);

}

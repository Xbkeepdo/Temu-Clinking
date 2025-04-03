package com.ch.clinking.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.PatternFile;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductDesigner;
import com.ch.clinking.entity.VO.ProductVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService extends IService<Product> {




 @Transactional(propagation = Propagation.REQUIRES_NEW)
 boolean updateProductBySkcId(String skcId, UpdateWrapper updateWrapper);


 Page<Product> getProducts(String nickName,
         String shopId,
         int page,      // 当前页，从 1 开始
         int size,      // 每页显示数量
         String searchKey, // 可选搜索关键词
         String currentTab
 );

 Page<ProductVO> searchTimeConvertProductToVO(Page<Product> page, LocalDateTime startTime, LocalDateTime endTime);













}

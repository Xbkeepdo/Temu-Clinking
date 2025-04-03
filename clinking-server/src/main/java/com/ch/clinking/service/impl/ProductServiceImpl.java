package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.PatternFile;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductCost;
import com.ch.clinking.entity.VO.ComputePage;
import com.ch.clinking.entity.VO.ProductVO;
import com.ch.clinking.mapper.ProductDesignerMapper;
import com.ch.clinking.mapper.ProductMapper;
import com.ch.clinking.service.ProductCostService;
import com.ch.clinking.service.ProductDesignerService;
import com.ch.clinking.service.ProductService;
import com.ch.clinking.service.SalesHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Resource ProductMapper productMapper;

    @Resource
    ProductDesignerService productDesignerService;

    @Resource
    ProductDesignerMapper productDesignerMapper;

    @Resource
    SalesHistoryService salesHistoryService;


    @Resource
    ProductCostService productCostService;


    @Override
    public boolean updateProductBySkcId(String skcId, UpdateWrapper updateWrapper) {

        if (productMapper.update(null, updateWrapper) > 0){
            return true;
        }
        else
            return false;

    }

    @Override
    public Page<Product> getProducts(String nickName, String shopId, int page, int size, String searchKey, String currentTab) {

        // 构造分页对象
        Page<Product> productPage = new Page<>(page, size);

        // 查询条件可以根据你的需要调整
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        if(nickName!=null){
            List<String> skcIds = productDesignerMapper.searchAllByDesigner(shopId,nickName);

            // 如果 skcIds 为空，则跳过 IN 过滤
            if (skcIds == null || skcIds.isEmpty()) {
                // 可以选择根据其他条件查询，或者不添加 skcId 的筛选条件
                skcIds = Collections.singletonList("0");  // 用 "0" 或者 NULL 作为占位符
            }

            queryWrapper.in("skcId", skcIds); // 过滤 skcId

        }



        queryWrapper.eq("shopId", shopId); // 根据 shopId 筛选

        // 根据 tab 参数筛选商品状态
        if (currentTab != null && !currentTab.equals("all")) {
            queryWrapper.eq("state", mapTabToState(currentTab)); // 根据 tab 映射状态
        }

        // 根据搜索关键字进行模糊搜索（可选）
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper.like("skcId", searchKey)
                    .or().like("title", searchKey)
                    .or().like("productNumber",searchKey));
        }
        return this.page(productPage, queryWrapper);
    }

    @Override
    public Page<ProductVO> searchTimeConvertProductToVO(Page<Product> page, LocalDateTime startTime, LocalDateTime endTime) {


        List<ProductVO> voList = page.getRecords().stream().map(product -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);

            //  处理时间范围
            LocalDateTime actualStart = startTime != null ? startTime : LocalDateTime.now().minusDays(30);
            LocalDateTime actualEnd = endTime != null ? endTime : LocalDateTime.now();

            //  计算销售数据
            double salesCount = salesHistoryService.getSalesCountByProduct(
                    product.getSkcId(),
                    actualStart,
                    actualEnd
            );
            //totalSales += salesCount;
            // 计算日均
            long days = Math.abs(ChronoUnit.DAYS.between(actualStart, actualEnd));
            days = days == 0 ? 1 : days;
            BigDecimal dailySales = BigDecimal.valueOf(salesCount)
                    .divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP);





            //计算总成本
            ProductCost productCost = productCostService.getProductCostBySkcId(product.getSkcId());

            if(productCost!=null){
                //总成本
                vo.setTotalCost(productCost.getTotal());
            }

            //计算设计师列表
            vo.setDesignerList(productDesignerService.getDesignerNameListBySkcId(product.getSkcId()));









            vo.setSalesCount(salesCount);
            vo.setDailySales(dailySales);

            return vo;
        }).collect(Collectors.toList());




        // 构建返回分页
        Page<ProductVO> resultPage = new Page<ProductVO>(page.getCurrent(), page.getSize(), page.getTotal());


        resultPage.setRecords(voList);


        return resultPage;
    }

    // 根据 tab 值映射对应的状态
    private String mapTabToState(String tab) {
        Map<String, String> tabStateMap = new HashMap<>();
        tabStateMap.put("unpublished", "1");
        tabStateMap.put("pendingSample", "3");
        tabStateMap.put("reviewing", "5");
        tabStateMap.put("pricing", "7");
        tabStateMap.put("pendingOrder", "10");
        tabStateMap.put("published", "12");
        tabStateMap.put("terminated", "13");

        return tabStateMap.getOrDefault(tab, "all");
    }
}

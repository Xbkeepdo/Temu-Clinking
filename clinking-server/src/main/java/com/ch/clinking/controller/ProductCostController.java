package com.ch.clinking.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductCost;
import com.ch.clinking.entity.Result;
import com.ch.clinking.mapper.ProductCostMapper;
import com.ch.clinking.service.ProductCostService;
import com.ch.clinking.service.impl.ProductCostServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/productCost")
public class ProductCostController {


    @Resource
    private ProductCostService productCostService;

    @Resource
    private ProductCostMapper productCostMapper;

    @GetMapping("/getTotalCost")
    public Result isEditProductCost(@RequestParam("skcId") String skcId){
       // System.out.println("=================skcId,getProductCost=====================" + skcId);
        ProductCost productCost = productCostService.getProductCostBySkcId(skcId);

        if(productCost == null){
            return Result.error(0,"无成本数据");
        }
        double total = productCost.calculateTotal();


        return  Result.success(total);
    }

    @PostMapping("/saveCost")
    public Result savaCost(@RequestBody ProductCost productCost){
        System.out.println("=================cost,savaCost=====================" + productCost);

       // productCostMapper
        //更新productCost有变化的部分
        productCostService.update(productCost,new UpdateWrapper<ProductCost>().eq("skcId",productCost.getSkcId()));



        return Result.success();
    }

    @GetMapping("/getCostData")
    public Result getCostData(@RequestParam("skcId") String skcId){
        System.out.println("=================skcId,getCostData=====================" + skcId);
        ProductCost productCost = productCostService.getProductCostBySkcId(skcId);

        if(productCost == null){
            return Result.error(0,"无成本数据");
        }


        return  Result.success(productCost);
    }


}

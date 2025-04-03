package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.clinking.entity.PageResult;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductDesigner;
import com.ch.clinking.entity.Result;
import com.ch.clinking.entity.VO.ComputePage;
import com.ch.clinking.entity.VO.ProductVO;
import com.ch.clinking.mapper.ProductDesignerMapper;
import com.ch.clinking.mapper.ProductMapper;
import com.ch.clinking.service.ProductDesignerService;
import com.ch.clinking.service.ProductService;
import com.ch.clinking.service.SalesHistoryService;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    ProductService productService;

    @Resource
    ProductMapper productMapper;

    @Resource
    ProductDesignerService productDesignerService;

    @Resource
    ProductDesignerMapper productDesignerMapper;

    @Resource
    SalesHistoryService salesHistoryService;



    @PostMapping("/updataProductNumber")
    public Result updataProductNumber(@RequestBody Map<String, Object> requestBody)
    {
        String skcId = (String) requestBody.get("skcId");
        String productNumber = (String) requestBody.get("productNumber");
        Product product = productMapper.selectOne(new QueryWrapper<Product>().eq("skcId", skcId));

        product.setProductNumber(productNumber);

        if (!productService.update(null,new UpdateWrapper<Product>().eq("skcId",skcId).set("productNumber",productNumber))){
            return Result.error(0,"更新货号失败");
        }

        return Result.success(product);

    }

    @GetMapping("/all")
    public Page<ProductVO> getProducts(
            @RequestParam String shopId,
            @RequestParam("page") int page,      // 当前页，从 1 开始
            @RequestParam("size") int size,      // 每页显示数量
            @RequestParam(required = false) String searchKey, // 可选搜索关键词
            @RequestParam(required = false) String currentTab
    ) {

        // 查询分页数据
        Page<Product> rawPage =  productService.getProducts(null,shopId,page,size,searchKey,currentTab);

        return productService.searchTimeConvertProductToVO(rawPage, null, null);
    }


    @GetMapping("/allBindProducts")
    public ComputePage<ProductVO> getBindProducts(
            @RequestParam String nickName,
            @RequestParam String shopId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String currentTab,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {

        //  处理时间范围
        LocalDateTime actualStart = startTime != null ? startTime : LocalDateTime.now().minusDays(30);
        LocalDateTime actualEnd = endTime != null ? endTime : LocalDateTime.now();

        //获取总销量
       List<String> skcIds=productDesignerMapper.searchAllByDesigner(shopId,nickName);
       double totalSalesCount = salesHistoryService.getTotalSalesCount(skcIds, actualStart, actualEnd);

       //获取PAge对象
        Page<Product> rawPage = productService.getProducts(nickName,shopId,page,size,searchKey,currentTab);


        //返回前端所需page结构
        ComputePage<ProductVO> result = new ComputePage<>();
        result.setPageData(productService.searchTimeConvertProductToVO(rawPage, startTime, endTime));
        result.setTotalSales(totalSalesCount);

        return result;
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchBySkcId(@RequestParam String skcId) {

        // 使用 MyBatis-Plus 的 QueryWrapper 实现模糊搜索
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("skcId", skcId);

        // 返回符合条件的商品列表
        return ResponseEntity.ok(productService.list(queryWrapper));
    }

    @GetMapping("/getOneBySkcId")
    public Result getOneBySkcId(@RequestParam String skcId) {

        // 使用 MyBatis-Plus 的 QueryWrapper 实现模糊搜索
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("skcId", skcId);

        // 返回符合条件的商品列表
        return Result.success(productService.getOne(queryWrapper));
    }


    @GetMapping("/getDesignerBind")
    public ResponseEntity<List<String>> getDesinerBindProduct(@RequestParam("shopId") String shopId,@RequestParam("nickName") String nickName){
    return ResponseEntity.ok(productDesignerMapper.searchAllByDesigner(shopId,nickName));
    }

    @PostMapping("/bind")
    public Result bindProductToDesigner(@RequestBody ProductDesigner productDesigner) {
        boolean success = productDesignerService.bindProduct(productDesigner);
        if (success) {
            return Result.success("绑定成功");
        } else {
            return Result.error(500, "绑定失败");
        }
    }

    // 解绑商品
    @PostMapping("/unbind")
    public Result unbindProductFromDesigner(@RequestBody ProductDesigner productDesigner) {
        boolean success = productDesignerService.unbindProduct(productDesigner);
        if (success) {
            return Result.success("解绑成功");
        } else {
            return Result.error(500, "解绑失败");
        }
    }

    // 查询是否绑定
    @GetMapping("/isBound")
    public Result checkIfBound(@RequestParam String skcId, @RequestParam String shopId, @RequestParam String name) {
        boolean isBound = productDesignerService.isProductBound(skcId, shopId, name);
        return Result.success(isBound);
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

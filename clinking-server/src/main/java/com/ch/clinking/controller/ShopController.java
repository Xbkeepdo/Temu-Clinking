package com.ch.clinking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.clinking.entity.*;
import com.ch.clinking.service.SalesHistoryShopBriefService;
import com.ch.clinking.service.SalesHistoryShopService;
import com.ch.clinking.service.ShopService;
import com.ch.clinking.service.UserShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @Resource
    private SalesHistoryShopService salesHistoryShopService;

    @Resource
    private SalesHistoryShopBriefService salesHistoryShopBriefService;

    @Resource
    private UserShopService userShopService;



    @GetMapping("/getShops")
    public ResponseEntity<List<Shop>> getAllShops() {

        return ResponseEntity.ok(shopService.list());
    }

    @PostMapping("/insert")
    public Result insertUser(@RequestBody Shop shop) {
        shopService.save(shop);
        return Result.success();
    }

    @PostMapping("/selectById")
    public Shop selectById(@RequestParam("shopId")String shopId) {
//        Shop shop = shopService.selectByShopId(shopId);
        Shop shop = shopService.getOne(new QueryWrapper<Shop>().eq("shop_id", shopId));
        System.out.println(shop);
        return shop;
    }

    @PostMapping("/updateBillCount")
    public Result updateSaveMoney(@RequestParam("billCount") String billCount, @RequestParam("shopId") String shopId) {
        System.out.println("updateBillCount   " + Integer.parseInt(billCount) + "   " + shopId);
        shopService.updateBillCount(Integer.parseInt(billCount), shopId);
        return Result.success();
    }

    @PostMapping("/updateShopName")
    public Result updateShopName(@RequestParam("teamName") String teamName, @RequestParam("shopId") String shopId) {
        shopService.updateShopName(teamName, shopId);
        return Result.success();
    }

    @PostMapping("/selectShopListByUserId")
    public List<Shop> selectShopListByUserId(@RequestParam("account")String account) {
        List<Shop> shopList = shopService.selectByAccount(account);
        System.out.println(shopList);
        return shopList;
    }

//    @PostMapping("/getShopsSaleInfo")
//    public List<HashMap<String, ShopSalesHistoryListDto>> getShopsSaleInfo(@RequestParam("shopId") String shopId) throws Exception {
//
//        Shop findeMainShop = shopService.selectByShopId(shopId);
//        List<Shop> shopList = shopService.list(new QueryWrapper<Shop>().eq("superAdminAccount", findeMainShop.getSuperAdminAccount()));
//        List<HashMap<String, ShopSalesHistoryListDto>> salesHistories = new ArrayList<>();
//        for (Shop shop : shopList) {
//            List<SalesHistoryShop> saleList = salesHistoryShopService.list(new QueryWrapper<SalesHistoryShop>().eq("shopId", shop.getShopId()).orderByDesc("createdTime").last("LIMIT 30"));
//            ShopSalesHistoryListDto shopSalesHistoryListDto = new ShopSalesHistoryListDto();
//            shopSalesHistoryListDto.setShopId(shop.getShopId());
//            shopSalesHistoryListDto.setSalesHistoryList(saleList);
//            HashMap <String, ShopSalesHistoryListDto> saleMap = new HashMap<>();
//            saleMap.put(shop.getShopName(),shopSalesHistoryListDto);
//            salesHistories.add(saleMap);
//        }
//        return salesHistories;
//    }

    @PostMapping("/getShopsSaleInfo")
    public List<SalesHistoryShop> getShopsSaleInfo(@RequestParam("shopId") String shopId) throws Exception {

        List<SalesHistoryShop> saleList = salesHistoryShopService.list(new QueryWrapper<SalesHistoryShop>().eq("shopId", shopId).orderByDesc("createdTime").last("LIMIT 30"));
        return saleList;
    }





}

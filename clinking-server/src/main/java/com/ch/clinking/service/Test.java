package com.ch.clinking.service;

import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.GetProductState;
import com.ch.clinking.service.scheduler.ProductScheduler;

import java.util.ArrayList;
import java.util.List;
import com.ch.clinking.service.scheduler.ProductScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class Test {

    @Resource
    private ProductScheduler productScheduler;
    @org.junit.jupiter.api.Test
    public void testGetShopProductList() throws Exception {



        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setShopType("标码"); // 二店
        CharmHop.setAccessToken("ukpiwfckgcu7ifboozkvs2n167whosh2xlorzqgtfszhcx83fexqqnyr");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        Shop Nalim = new Shop();
        Nalim.setShopId("634418218940910");
        Nalim.setShopType("标码");
        Nalim.setAccessToken("iynrezazrim90q1in52zmbefii1g2jcdubytx5ddeoh3u5bnaochdg03");
        Nalim.setAppKey("d422fea5868d0a2da1723c12274c87ec");
        Nalim.setAppSecret("fb75dc07fe580fdd156002b831f6b7144486126a");

        Shop mihu = new Shop();
        mihu.setShopId("634418210011963");
        mihu.setShopType("标码");
        mihu.setAccessToken("8hkyivcvewxuptpjzrsg5cwhrrjstkmsq066ajq8lycwg3vurlz80yn2");
        mihu.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        mihu.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        int totalCount = 0;
        int currentPage = 1;
        int pageSize = 20;
        int totalPage = 999;

        List<String>  skuList =new ArrayList<>();
        skuList.add("2728196582");

        String productStateJsonDate = GetProductState.getInstance().GetSkcListState(mihu, currentPage, pageSize,skuList);
        System.out.println(productStateJsonDate);

//        List<Product> productList =productScheduler.getShopProductList(CharmHop);
//        System.out.println(productList);
    }
}

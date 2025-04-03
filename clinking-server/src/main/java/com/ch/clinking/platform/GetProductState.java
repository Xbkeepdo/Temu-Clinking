package com.ch.clinking.platform;

import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.util.Config;
import com.ch.clinking.platform.util.SentRequest;
import lombok.Getter;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ch.clinking.platform.util.Config.url;

public class GetProductState {

    @Getter
    private static final GetProductState instance = new GetProductState();


    public String GetSkcListState(Shop shop, int page, int pageSize,List<String> skuList) throws Exception {
        //  请求类型
        String type = "bg.product.search";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("access_token", shop.getAccessToken());
//        List<String> productSkuIdList = new ArrayList<>();
//
//        productSkuIdList.add(s);
        params.put("productSkuIdList", skuList.toString());


        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }




}

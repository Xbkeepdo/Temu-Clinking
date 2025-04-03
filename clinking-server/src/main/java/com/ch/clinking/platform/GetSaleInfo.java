package com.ch.clinking.platform;

import com.ch.clinking.entity.SaleInfo;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.util.Config;
import com.ch.clinking.platform.util.SentRequest;
import com.ch.clinking.service.SaleInfoService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ch.clinking.platform.util.Config.url;

public class GetSaleInfo{

    @Getter
    private static final GetSaleInfo instance = new GetSaleInfo();

    public void GetOneSaleInfoBySkcV2(Shop shop, String skcId) throws Exception {
        //  请求类型
        String type = "bg.goods.salesv2.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "1");
        List<String> productSkcIdList = new ArrayList<>();
        productSkcIdList.add(skcId);
        params.put("productSkcIdList", productSkcIdList.toString());
//        List<String> selectStatusList = new ArrayList<>();
//        selectStatusList.add("12");
//        params.put("selectStatusList", selectStatusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        SentRequest.sendPostRequest(shop, url, params);
    }

    public String GetOneTestBySkc(Shop shop, String type) throws Exception {

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "1");
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

    public String GetOneSaleInfoBySkc(Shop shop, String skcId) throws Exception {

        //  请求类型
        String type = "bg.goods.sales.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "1");
        List<String> productSkcIdList = new ArrayList<>();
        productSkcIdList.add(skcId);
        params.put("productSkcIdList", productSkcIdList.toString());
//        List<String> selectStatusList = new ArrayList<>();
//        selectStatusList.add("12");
//        params.put("selectStatusList", selectStatusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

    public String GetOneProductInfoBySkc(Shop shop, String skcId) throws Exception {

        //  请求类型
        String type = "bg.goods.list.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("page", "1");
        List<String> productSkcIdList = new ArrayList<>();
        productSkcIdList.add(skcId);
        params.put("productSkcIds", productSkcIdList.toString());
//        List<String> selectStatusList = new ArrayList<>();
//        selectStatusList.add("12");
//        params.put("selectStatusList", selectStatusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

    public String IntoOneShiporderByOrderSn(Shop shop, String OrderSn) throws Exception {

        //  请求类型
        String type = "bg.shiporder.staging.add";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        // 构造 joinInfoList
        JsonArray joinInfoList = new JsonArray();
        JsonObject joinInfo = new JsonObject();
        joinInfo.addProperty("deliveryAddressType", 1);
        joinInfo.addProperty("subPurchaseOrderSn", OrderSn); // 请根据需要替换字符串
        joinInfoList.add(joinInfo);

        params.put("joinInfoList", new Gson().toJson(joinInfoList));
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

    public String GetShiporder(Shop shop) throws Exception {

        //  请求类型
        String type = "bg.shiporderv2.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        params.put("access_token", shop.getAccessToken());
        params.put("status", "0");
        params.put("sortType", "0");
        //1 已送货
        //2 已收货
//        params.put("urgencyType", "0");

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }


    public String GetListSaleInfoBySkcV2(Shop shop, List<String> skcIdList) throws Exception {

        //  请求类型
        String type = "bg.goods.salesv2.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "500");
        params.put("productSkcIdList", skcIdList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }



    public String GetListSaleInfoBySkc(Shop shop, List<String> skcIdList) throws Exception {

        //  请求类型
        String type = "bg.goods.sales.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "500");
        params.put("productSkcIdList", skcIdList.toString());
//        List<String> selectStatusList = new ArrayList<>();
//        selectStatusList.add("12");
//        params.put("selectStatusList", selectStatusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }




}

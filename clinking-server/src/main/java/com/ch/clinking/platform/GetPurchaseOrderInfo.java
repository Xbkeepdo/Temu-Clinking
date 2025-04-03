package com.ch.clinking.platform;

import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.util.Config;
import com.ch.clinking.platform.util.SentRequest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ch.clinking.platform.util.Config.url;

public class GetPurchaseOrderInfo {

    @Getter
    private static final GetPurchaseOrderInfo instance = new GetPurchaseOrderInfo();

    // 已接单待发货备货单
    public String GetPurchaseOrderV2(Shop shop, int pageNo, int pageSize) throws Exception {
        //  请求类型
        String type = "bg.purchaseorderv2.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));  // 需要判断是否有下一页
        params.put("todayCanDeliver", "true");  // 是否今日可发货
        List<String> statusList = new ArrayList<>();
        statusList.add("1"); // 已接单，待发货
        params.put("statusList", statusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

    // 待接单备货单
    public String GetPurchaseOrderV2WaitForOrders(Shop shop) throws Exception {
        //  请求类型
        String type = "bg.purchaseorderv2.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("pageNo", "1");
        params.put("pageSize", "50");  // 需要判断是否有下一页
        params.put("todayCanDeliver", "true");  // 是否今日可发货
        List<String> statusList = new ArrayList<>();
        statusList.add("0"); // 已接单，待发货
        params.put("statusList", statusList.toString());
        params.put("access_token", shop.getAccessToken());

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }

}

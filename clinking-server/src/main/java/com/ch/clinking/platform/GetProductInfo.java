package com.ch.clinking.platform;

import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.util.Config;
import com.ch.clinking.platform.util.SentRequest;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static com.ch.clinking.platform.util.Config.*;

public class GetProductInfo {

    @Getter
    private static final GetProductInfo instance = new GetProductInfo();

    public String GetProductInfoList(Shop shop, int page, int pageSize) throws Exception {
        //  请求类型
        String type = "bg.goods.list.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("access_token", shop.getAccessToken());

        LocalDateTime localDateTime = LocalDateTime.of(2023, Month.OCTOBER, 4, 0, 0, 0);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
        long timestampS = zonedDateTime.toInstant().toEpochMilli();
        params.put("createdAtStart", String.valueOf(timestampS));

//        LocalDateTime localDateTimeE = LocalDateTime.of(2024, Month.DECEMBER, 22, 23, 59, 59);
//        ZonedDateTime zonedDateTimeE = localDateTimeE.atZone(ZoneOffset.UTC);
//        long timestampE = zonedDateTimeE.toInstant().toEpochMilli();

//        long timestamp = Instant.now().toEpochMilli();

//        params.put("createdAtEnd", String.valueOf(timestampE));
 //       params.put("skcSiteStatus", skcSiteStatus);

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }


    public String GetProductInfoListStatusOne(Shop shop, int page, int pageSize) throws Exception {
        //  请求类型
        String type = "bg.goods.list.get";

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());
        params.put("type", type);
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("access_token", shop.getAccessToken());

        LocalDateTime localDateTime = LocalDateTime.of(2025, Month.JANUARY, 4, 0, 0, 0);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
        long timestampS = zonedDateTime.toInstant().toEpochMilli();
        params.put("createdAtStart", String.valueOf(timestampS));

//        LocalDateTime localDateTimeE = LocalDateTime.of(2024, Month.DECEMBER, 22, 23, 59, 59);
//        ZonedDateTime zonedDateTimeE = localDateTimeE.atZone(ZoneOffset.UTC);
//        long timestampE = zonedDateTimeE.toInstant().toEpochMilli();

//        long timestamp = Instant.now().toEpochMilli();

//        params.put("createdAtEnd", String.valueOf(timestampE));
        params.put("skcSiteStatus", "0");

        // 发送请求
        return SentRequest.sendPostRequest(shop, url, params);
    }




    public void GetOneProductInfoBySkc(Shop shop, String skcId) throws Exception {

        //  请求类型
        String type = "bg.goods.list.get";


//        // 构造业务参数
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("carrierId", "699272611");
//        requestBody.put("trackingNumber", "270324232756");

        // 构造公共参数
        Map<String, String> params = Config.getInstance().getPublicRequest(shop.getAppKey());;
        params.put("type", type);
//        params.put("timestamp", String.valueOf(timestamp));
//        params.put("app_key", appKey);
//        params.put("data_type", "JSON");
//        params.put("access_token", accessToken);
        params.put("pageNo", "1");
        params.put("pageSize", "1");
        List<String> productSkcIds = new ArrayList<>();
        productSkcIds.add(skcId);
        params.put("productSkcIds", productSkcIds.toString());

        // 发送请求
        SentRequest.sendPostRequest(shop, url, params);
    }



}

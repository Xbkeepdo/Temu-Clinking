package com.ch.clinking.platform.util;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static String url = "https://openapi.kuajingmaihuo.com/openapi/router";
//    public static String appKey = "6a8a177e4c51edb61c24f09f01c3a9e9";
//    public static String accessToken = "xmdtb1sjw8vpxic0trfu8fnst6jsz5lxhzanpjkfbnnafgsrsaacp1nn";
//    public static String appSecret = "740020759d9ace02c653cc5236fa81454750368b";

    private static final Config instance = new Config();

    // 构造公共参数
//    public static Map<String, String> params = new HashMap<>();

    public Config() {
//        params.put("type", type);

    }

    public static Config getInstance() {
        return instance;
    }

    public Map<String, String> getPublicRequest(String appKey) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("app_key", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("data_type", "JSON");

        return params;
//        params.put("pageNo", "1");
//        params.put("pageSize", "1");
//        List<String> productSkcIds = new ArrayList<>();
//        productSkcIds.add("6708259901");
//        productSkcIds.add("6204870210");
//        params.put("productSkcIds", productSkcIds.toString());
        // 生成签名


        // 发送请求
//        SentRequest.sendPostRequest(url, params);
    }


}

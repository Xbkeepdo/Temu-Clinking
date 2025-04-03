package com.ch.clinking.platform.util;

import com.ch.clinking.entity.Shop;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class SentRequest {

    // 发送POST请求
    public static String sendPostRequest(Shop shop, String url, Map<String, String> params) throws Exception {
        String sign = GenerateSign.generateSign(params, shop.getAppSecret());
        params.put("sign", sign);
        // 将参数转为JSON格式
        Gson gson = new Gson();
        String jsonParams = gson.toJson(params);

        // 设置HTTP连接
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);

        // 发送请求数据
        String requestData = jsonParams;
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 打印响应
        System.out.println("Response: " + response.toString());
        return response.toString();
    }

}

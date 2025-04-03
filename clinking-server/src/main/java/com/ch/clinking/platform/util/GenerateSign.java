package com.ch.clinking.platform.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class GenerateSign {

    // 生成MD5签名
    public static String generateSign(Map<String, String> params, String appSecret) throws Exception {
        // 按照key的ASCII码升序排序
        Map<String, String> sortedParams = new TreeMap<>(params);

        // 拼接key和value形成签名字符串
        StringBuilder signString = new StringBuilder(appSecret);
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            signString.append(entry.getKey()).append(entry.getValue());
        }
        signString.append(appSecret);

        // 生成MD5哈希
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(signString.toString().getBytes(StandardCharsets.UTF_8));

        // 转换为十六进制字符串
        StringBuilder result = new StringBuilder();
        for (byte b : hashInBytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

}

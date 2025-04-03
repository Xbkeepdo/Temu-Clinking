package com.ch.clinking.platform.test;

import com.ch.clinking.entity.NoInDataBase.PurchaseOrder;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.GetPurchaseOrderInfo;
import com.ch.clinking.service.SaleInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class testSent {

    @Resource
    private static SaleInfoService saleInfoService;



    public static void main(String[] args) throws Exception {




//        GetSaleInfo.getInstance().GetOneSaleInfoBySkcV2("fsbhwvikdykbjcg85smyxry6h45avvadnoqfd2djlplfvev6c1aipyki","5055341110");
//        GetSaleInfo.getInstance().GetOneSaleInfoBySkc("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm","9117990380");
        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setAccessToken("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        Shop Nalim = new Shop();
        Nalim.setShopId("634418218940910"); // 二店
        Nalim.setAccessToken("vtjfmwojsqncpsb2nbcxfxs0u6gb3tqltqumeitsgkligajgxsvcls2i");
        Nalim.setAppKey("d422fea5868d0a2da1723c12274c87ec");
        Nalim.setAppSecret("fb75dc07fe580fdd156002b831f6b7144486126a");


//        GetSaleInfo.getInstance().GetOneSaleInfoBySkcV2(shop,"1333608764"); // 销售信息测试
        int total = 0;
        int currentPage = 2;
        int pageSize = 5;
        String orderJsonDate = GetPurchaseOrderInfo.getInstance().GetPurchaseOrderV2(Nalim, currentPage, pageSize);
        // 创建 JSONObject 对象
        JSONObject jsonObject = new JSONObject(orderJsonDate);
        // 获取 "result" 对象
        JSONObject result = jsonObject.getJSONObject("result");
        // 提取 "total" 字段
        total = result.getInt("total");

        // 解析 JSON 数据
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.readValue(orderJsonDate, Map.class);

        List<PurchaseOrder> orderList = getListPurchaseOrderInfo(data);

        System.out.println(orderList);
//        GetState.getInstance().GetOneStateBySkc("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm","7800747250");
//        GetState.getInstance().GetAllState("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm");
//        String jsonResponse  = GetSaleInfo.getInstance().GetOneProductInfoBySkc("uyubzm34iod5zv4h9mo5rcqzzj2ozvgluakxrwmuyuvtaphsotlxhcam", "3417128314");
//        String jsonResponse  = GetSaleInfo.getInstance().IntoOneShiporderByOrderSn("d5hl2kwhkaxuwgh4yrjpvddj8jfkxrfhjfuxaeh6bnjrkzxa1mqt9yzm", "WB2410102896512");

//        while (true) {
//            String jsonResponse="";
//            JSONObject jsonObject = null;
//
//            while (true) {
//                jsonResponse  = GetSaleInfo.getInstance().IntoOneShiporderByOrderSn("nxbvyk3dtk8qqrlm6uxvebeutkflbircrw95yoeabepmtfe5yn8yygrx", "WB2410182733656");
//                jsonObject = new JSONObject(jsonResponse);
//                boolean resultSuccess = jsonObject.getBoolean("success");
//                if (resultSuccess) break;
//            }
//
//            JSONArray joinErrorList = jsonObject.getJSONObject("result").getJSONArray("joinErrorList");
//
//            String errorMsg = joinErrorList.getJSONObject(0).getString("errorMsg");
////            if (errorMsg.equals("商家备货火爆，未成功加入发货台，请稍后重试")) {
////            }
//            if (errorMsg == null || errorMsg.isEmpty()) {
//                break;
//            }
//            Random random = new Random();
//            int randomNumber = random.nextInt(1500) + 500;
//            Thread.sleep(randomNumber);
//        }

//        while (true) {
//            String jsonResponse  = GetSaleInfo.getInstance().GetShiporder("d5hl2kwhkaxuwgh4yrjpvddj8jfkxrfhjfuxaeh6bnjrkzxa1mqt9yzm");
//            // 创建JsonObject对象
//            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
//
//            // 提取success字段
//            boolean success = jsonObject.get("success").getAsBoolean();
//            if (success) {
//                break;
//            }
//        }


    }

    public static List<PurchaseOrder> getListPurchaseOrderInfo(Map<String, Object> data) {
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();

        // 获取 'result' 部分
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> subOrderForSupplierList = (List<Map<String, Object>>) result.get("subOrderForSupplierList");

        // 遍历每个子订单
        for (Map<String, Object> subOrder : subOrderForSupplierList) {

            // 创建 PurchaseOrder 对象
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            // 提取需要的字段并设置
            purchaseOrder.setSkcId(subOrder.get("productSkcId").toString());
            purchaseOrder.setOriginalPurchaseOrderSn(subOrder.get("originalPurchaseOrderSn").toString());
            purchaseOrder.setSubPurchaseOrderSn(subOrder.get("subPurchaseOrderSn").toString());
            purchaseOrder.setProductSkcPicture(subOrder.get("productSkcPicture").toString());
            purchaseOrder.setProductName(subOrder.get("productName").toString());
            purchaseOrder.setFirst((Boolean) subOrder.get("isFirst"));
            purchaseOrder.setUrgencyType((Integer) subOrder.get("urgencyType"));
            // 获取是否可以加入发货台
            purchaseOrder.setCanJoinDeliverPlatform((Boolean) subOrder.get("isCanJoinDeliverPlatform"));

            // 获取采购数量
            Map<String, Object> skuQuantityTotalInfo = (Map<String, Object>) subOrder.get("skuQuantityTotalInfo");
            purchaseOrder.setPurchaseQuantity((Integer) skuQuantityTotalInfo.get("purchaseQuantity"));

            // 获取采购数量
            Map<String, Object> skuTimeInfo = (Map<String, Object>) subOrder.get("deliverInfo");
            long receivedTimestamp = (long) skuTimeInfo.get("expectLatestDeliverTimeOrDefault");

            purchaseOrder.setUrgencyLevel(getPurchaseOrderInfo(receivedTimestamp));

            System.out.println(purchaseOrder.getOriginalPurchaseOrderSn());

            // 精确发货时间到分钟
            // 将时间戳转换为 Instant
            Instant instant = Instant.ofEpochMilli(receivedTimestamp);
            // 将接收到的时间转换为 Date（精确到分钟）
            LocalDateTime receivedDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().truncatedTo(ChronoUnit.MINUTES);
            Date truncatedReceivedDate = Date.from(receivedDateTime.atZone(ZoneId.systemDefault()).toInstant());
            purchaseOrder.setExpectLatestDeliverTime(truncatedReceivedDate);


            // 添加到列表
            purchaseOrderList.add(purchaseOrder);

        }

        // 返回所有的 PurchaseOrder 对象
        return purchaseOrderList;
    }

    private static int getPurchaseOrderInfo(long receivedTimestamp) {

        // 将时间戳转换为 Instant
        Instant instant = Instant.ofEpochMilli(receivedTimestamp);

        // 将接收到的时间转换为 Date（精确到分钟）
        LocalDateTime receivedDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().truncatedTo(ChronoUnit.MINUTES);
        Date truncatedReceivedDate = Date.from(receivedDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // 获取当前日期的 Date（只关心年月日，忽略时分秒）
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS); // 今天的开始时刻（00:00）
        Date todayDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        // 将 Date 转换为 LocalDate（只关心年月日）
        LocalDate receivedLocalDate = truncatedReceivedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate todayLocalDate = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 比较日期（只关心年月日）
        if (receivedLocalDate.isBefore(todayLocalDate)) {
            System.out.println("接收到的日期早于今天");
            return 0;
        } else if (receivedLocalDate.isEqual(todayLocalDate)) {
            System.out.println("接收到的日期是今天");
            return 1;
        } else {
            System.out.println("接收到的日期晚于今天");
            return 2;
        }

    }

    private static String CalculateMakeOrder(int temu_stock, int on_way, int self_stock, int S_i, int W, int D, int ZZ) {

        double S = 0;
        if (S_i != 0) {
            S = Double.parseDouble(String.valueOf(S_i)) / 7.0;
        }

        System.out.println("temu_stock:"+String.valueOf(temu_stock)+" on_way:"+String.valueOf(on_way)+" self_stock:"+String.valueOf(self_stock)+" S:"+String.valueOf(S)+" S_i:"+String.valueOf(S_i)+" W:"+String.valueOf(W)+" D:"+String.valueOf(D)+" ZZ:"+String.valueOf(ZZ));


        // S 平均销售数量
        // W = 7 生产周期
        // D = 5 每单补货量的天数
        // ZZ 真正做的数量
        double Num = 0; // 需要下单的数量

        while ((temu_stock + on_way + self_stock + ZZ + Num - S * W) < 0) {
            Num += D * S;
            System.out.println((temu_stock + on_way + self_stock + ZZ + Num - S * W));
            System.out.println(Num);
        }

//        if ((temu_stock + on_way + self_stock - S * W) <= 0) {
//            binding.calculateMakeOrderUrgent.setText("是");
//        } else {
//            binding.calculateMakeOrderUrgent.setText("否");
//        }


        String formattedValue = String.format("%.0f", Num);

        System.out.println(formattedValue);

        return formattedValue;

    }



//    public static String getOneSaleInfoBySkc(String accessToken, String skcId) throws Exception {
//        return GetSaleInfo.getInstance().GetOneSaleInfoBySkc(accessToken, skcId);
//    }
//
//    public static String getListSaleInfoBySkc(String accessToken, List<String> skcIdList) throws Exception {
//        return GetSaleInfo.getInstance().GetListSaleInfoBySkc(accessToken, skcIdList);
//    }

//    private static void getListProductInfoBySkc() throws Exception {
//        List<String> productSkcIds = new ArrayList<>();
//
//        productSkcIds.add("6708259901");
//        productSkcIds.add("6204870210");
//
//
//        GetProductInfo.getInstance().GetListProductInfoBySkc(productSkcIds);
//    }
//
//    private static void getOneProductInfoBySkc() throws Exception {
//
//        GetProductInfo.getInstance().GetOneProductInfoBySkc("6708259901");
//
//    }




//    // 生成MD5签名
//    public static String generateSign(Map<String, String> params, String appSecret) throws Exception {
//        // 按照key的ASCII码升序排序
//        Map<String, String> sortedParams = new TreeMap<>(params);
//
//        // 拼接key和value形成签名字符串
//        StringBuilder signString = new StringBuilder(appSecret);
//        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
//            signString.append(entry.getKey()).append(entry.getValue());
//        }
//        signString.append(appSecret);
//
//        // 生成MD5哈希
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] hashInBytes = md.digest(signString.toString().getBytes(StandardCharsets.UTF_8));
//
//        // 转换为十六进制字符串
//        StringBuilder result = new StringBuilder();
//        for (byte b : hashInBytes) {
//            result.append(String.format("%02X", b));
//        }
//        return result.toString();
//    }
//
//    // 发送POST请求
//    public static void sendPostRequest(String url, Map<String, String> params, Map<String, Object> body) throws Exception {
//        // 将参数转为JSON格式
//        Gson gson = new Gson();
//        String jsonParams = gson.toJson(params);
//        String jsonBody = gson.toJson(body);
//
//        // 设置HTTP连接
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//        con.setDoOutput(true);
//
//        // 发送请求数据
//        String requestData = jsonParams + jsonBody;
//        try (OutputStream os = con.getOutputStream()) {
//            byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//
//        // 读取响应
//        int responseCode = con.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        // 打印响应
//        System.out.println("Response: " + response.toString());
//    }
}

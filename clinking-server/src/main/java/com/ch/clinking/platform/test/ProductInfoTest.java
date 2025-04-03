package com.ch.clinking.platform.test;

import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductDesigner;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.platform.GetProductInfo;
import com.ch.clinking.platform.GetProductState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductInfoTest {

    public static void main(String[] args) throws Exception {

        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setShopType("标码"); // 二店
        CharmHop.setAccessToken("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        Shop Nalim = new Shop();
        Nalim.setShopId("634418218940910");
        CharmHop.setShopType("标码");
        Nalim.setAccessToken("vtjfmwojsqncpsb2nbcxfxs0u6gb3tqltqumeitsgkligajgxsvcls2i");
        Nalim.setAppKey("d422fea5868d0a2da1723c12274c87ec");
        Nalim.setAppSecret("fb75dc07fe580fdd156002b831f6b7144486126a");

        int totalCount = 0;
        int currentPage = 1;
        int pageSize = 1;
        int totalPage = 999;

        while (currentPage <= totalPage) {
            String productJsonDate = GetProductInfo.getInstance().GetProductInfoList(CharmHop, currentPage, pageSize);
            // 创建 JSONObject 对象
            JSONObject jsonObject = new JSONObject(productJsonDate);
            // 获取 "result" 对象
            JSONObject result = jsonObject.getJSONObject("result");

            // 提取 "total" 字段
            totalCount = result.getInt("totalCount");
            totalPage = (int) Math.ceil((double) totalCount / pageSize);

            // 取数据
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(productJsonDate, Map.class);
            List<Product> productList = getListProductInfo(data, CharmHop);

            System.out.println(productList);

            currentPage++;
        }


    }


    public static List<Product> getListProductInfo(Map<String, Object> data, Shop shop) {
        List<Product> productList = new ArrayList<>();

        // 获取 'result' 部分
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> producRectList = (List<Map<String, Object>>) result.get("data");

        // 遍历每个产品
        for (Map<String, Object> productRec : producRectList) {

            // 创建 Product 对象
            Product product = new Product();

            // 提取需要的字段并设置
            product.setShopId(shop.getShopId());
            product.setSpuId(productRec.get("productId").toString());
            product.setSkcId(productRec.get("productSkcId").toString());
            bindingDesigner(productRec.get("productName").toString(), productRec.get("productSkcId").toString());  // 绑定设计师
            product.setTitle(productRec.get("productName").toString());
            product.setCreateTime((Long) productRec.get("createdAt"));


            // 获取分类信息
            Map<String, Object> leafCat = (Map<String, Object>) productRec.get("leafCat");
            String catName = (String) leafCat.get("catName");
            product.setCatName(catName);

            // 获取主图 URL
            product.setFirstImage((String) productRec.get("mainImageUrl"));
            product.setShopType(shop.getShopType());

            // 获取 SKU 列表
            List<Map<String, Object>> productSkuSummaries = (List<Map<String, Object>>) productRec.get("productSkuSummaries");

            // 遍历每个 SKU
            for (Map<String, Object> sku : productSkuSummaries) {
                List<Map<String, Object>> productSkuSpecList = (List<Map<String, Object>>) sku.get("productSkuSpecList");

                // 遍历每个规格，寻找尺码
                for (Map<String, Object> spec : productSkuSpecList) {
                    String parentSpecName = (String) spec.get("parentSpecName");
                    if ("尺码".equals(parentSpecName)) {
                        String specName = (String) spec.get("specName");
                        product.setSkuList(product.getSkuList() + "-" + specName);  // 假设 Product 类中有 setSize 方法
                        break; // 找到尺码后可以跳出循环
                    }
                }
            }

            // 添加到列表
            productList.add(product);
        }

        // 返回所有的 Product 对象
        return productList;
    }

    private static void bindingDesigner(String productName, String productSkcId) {

        if (productName.contains("【叶】")) {
            ProductDesigner productDesigner = new ProductDesigner();
            productDesigner.setSkcId(productSkcId);
            productDesigner.setName("Jodie");

        }
        if (productName.contains("招财定向")) {

        }
        if (productName.contains("念念定向") || productName.contains("念念精选")) {

        }

    }


}

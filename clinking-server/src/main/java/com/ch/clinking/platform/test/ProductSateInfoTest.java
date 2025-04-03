package com.ch.clinking.platform.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.clinking.entity.*;
import com.ch.clinking.mapper.ProductMapper;
import com.ch.clinking.platform.GetProductInfo;
import com.ch.clinking.platform.GetProductState;
import com.ch.clinking.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONObject;
import com.ch.clinking.service.scheduler.ProductScheduler;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.*;

import static com.ch.clinking.platform.test.ProductInfoTest.getListProductInfo;

public class ProductSateInfoTest {
     ProductScheduler  productScheduler ;


    private static List<Shop> shopList;
    private static List<User> userList;

    @Resource
    private static  ProductMapper productMapper;

    @Resource
    private  static ShopService shopService;
    public  static  void main(String[] args) throws Exception {





        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setShopType("标码"); // 二店
        CharmHop.setAccessToken("utsumvs8yuewnlqguylwran9qac0sb5lyihyihsam7pioa9jyg7sbktm");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        Shop Nalim = new Shop();
        Nalim.setShopId("634418218940910");
        Nalim.setShopType("标码");
        Nalim.setAccessToken("vtjfmwojsqncpsb2nbcxfxs0u6gb3tqltqumeitsgkligajgxsvcls2i");
        Nalim.setAppKey("d422fea5868d0a2da1723c12274c87ec");
        Nalim.setAppSecret("fb75dc07fe580fdd156002b831f6b7144486126a");


        Shop mihu = new Shop();
        mihu.setShopId("634418210011963");
        mihu.setShopType("标码");
        mihu.setAccessToken("mrreoehdcg2g8ydyyaqgpugl3ghuz20xn6w0appw9j2ikjpbvueqdgih");
        mihu.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        mihu.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        List<Shop> shopList = new ArrayList<>();
        shopList.add(CharmHop);
        shopList.add(mihu);
        shopList.add(Nalim);


       int totalCount = 0;
       int currentPage = 1;
        int pageSize = 20;
        int totalPage = 999;




       // for (Shop shop : shopList) {
                //更新商店product状态
                updataShopProductState(CharmHop, currentPage, pageSize);

                //根据product进行数据库更新

      //  }

//
//
//        Map<String,String> statusMap = new HashMap<>();
//        buildStateMap(CharmHop,currentPage,pageSize,statusMap);
//





//       while (currentPage <= totalPage) {
//            String productStateJsonDate = GetProductState.getInstance().GetSkcListState(mihu, currentPage, pageSize,skuList.toString());
//            // 创建 JSONObject 对象
//            JSONObject jsonObject = new JSONObject(productStateJsonDate);
//            // 获取 "result" 对象
//            JSONObject result = jsonObject.getJSONObject("result");
//
//            // 提取 "total" 字段
//            totalCount = result.getInt("total");
//            totalPage = (int) Math.ceil((double) totalCount / pageSize);
//
//            // 取数据
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> data = objectMapper.readValue(productStateJsonDate, Map.class);
//            List<Product> productList = getListProductInfo(data, mihu);
//
//            System.out.println(productList);
//
//            currentPage++;
//        }


    }



    public static List<Product> getShopProductList(Shop shop)throws Exception{
        int totalCount = 0;
        int currentPage = 1;
        int pageSize = 2;
        int totalPage = 999;

        //xb
        List<Product> productList = new ArrayList<>();

        while (currentPage <= totalPage) {
            String productJsonDate = GetProductInfo.getInstance().GetProductInfoList(shop, currentPage, pageSize);
            // 创建 JSONObject 对象
            JSONObject jsonObject = new JSONObject(productJsonDate);
            // 获取 "result" 对象
            JSONObject result = jsonObject.getJSONObject("result");


            if (result.getInt("totalCount") == 0) {
                break;
            }
            // 提取 "total" 字段
            totalCount = result.getInt("totalCount");
            totalPage = (int) Math.ceil((double) totalCount / pageSize);

            // 取数据
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(productJsonDate, Map.class);
//                List<Product> productList = getListProductInfo(data, shop);

            productList.addAll(getListProductInfo(data, shop));
             //  System.out.println(productList);

            currentPage++;
        }

        return productList;
    }


    public  static List<Product> getListProductInfo(Map<String, Object> data, Shop shop) {
        List<Product> productList = new ArrayList<>();

        // 获取 'result' 部分
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> producRectList = (List<Map<String, Object>>) result.get("data");

        // 遍历每个产品
        for (Map<String, Object> productRec : producRectList) {

            String skcId = productRec.get("productSkcId").toString();


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

                product.setsSku(sku.get("productSkuId").toString());

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



    public  List<Product> getListStateProductInfo(Map<String, Object> data, Shop shop) {
        List<Product> productList = new ArrayList<>();

        // 获取 'result' 部分
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> producStateRectList = (List<Map<String, Object>>) result.get("dataList");

        // 遍历每个产品
        for (Map<String, Object> productState : producStateRectList) {

            // 创建 Product 对象
            Product product = new Product();

            // 提取需要的字段并设置
            product.setShopId(shop.getShopId());
            product.setSpuId(productState.get("productId").toString());
            product.setSkcId(productState.get("productSkcId").toString());
            bindingDesigner(productState.get("productName").toString(), productState.get("productSkcId").toString());  // 绑定设计师
            product.setTitle(productState.get("productName").toString());
            product.setCreateTime((Long) productState.get("createdAt"));

            // 获取分类信息
            Map<String, Object> leafCat = (Map<String, Object>) productState.get("leafCat");
            String catName = (String) leafCat.get("catName");
            product.setCatName(catName);

            // 获取主图 URL
            product.setFirstImage((String) productState.get("mainImageUrl"));
            product.setShopType(shop.getShopType());

            // 获取 SKU 列表
            List<Map<String, Object>> productSkuSummaries = (List<Map<String, Object>>) productState.get("productSkuSummaries");

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

    //建立一个商店所有商品的skcid到state的map
    public static void updataShopProductState(Shop shop,int page,int pagesize) throws Exception {

        List<Product> productList = getShopProductList(shop);
        List<String>  skuList =new ArrayList<>();
        Map<String,String> statusMap = new HashMap<>();
        //获取所有商品的代表性skuid
        for( Product product : productList){
           skuList.add(product.getsSku());
            System.out.println(product.getSkcId());
        }

        //获取产品生命周期信息
            String productStateJsonDate = GetProductState.getInstance().GetSkcListState(shop, page, pagesize,skuList);

        //json数据映射为MAP
        SkcToStatusMapping(productStateJsonDate,statusMap);


        QueryWrapper<Product> wrapper = new QueryWrapper<Product>();
        //根据map更新product
        for( Product product : productList){
            product.setState(statusMap.get(product.getSkcId()));
            System.out.println(product.getSkcId() + " = "  +product.getState());
          //  productmapper
            wrapper.select(product.getSkcId());
            productMapper.update(product,wrapper);
        }

        //System.out.println(statusMap);

    }

    //对生命周期返回参数进行skcid到state的映射
    public static void SkcToStatusMapping(String json, Map<String,String> statusMap) throws JsonProcessingException {
        // 取数据
        ObjectMapper objectMapper = new ObjectMapper();

        // 将 JSON 字符串解析为 JsonNode
        JsonNode rootNode = objectMapper.readTree(json);

        // 遍历 dataList 提取 skcId 和 selectStatus
        JsonNode dataList = rootNode.path("result").path("dataList");
        if (dataList.isArray()) {
            for (JsonNode dataItem : dataList) {
                JsonNode skcList = dataItem.path("skcList");
                if (skcList.isArray()) {
                    for (JsonNode skcItem : skcList) {
                        String skcId = skcItem.path("skcId").toString();
                        String selectStatus = skcItem.path("selectStatus").toString();
                        statusMap.put(skcId, selectStatus);
                    }
                }
            }
        }

        // 打印结果
       // System.out.println("SKC to Status Mapping: " + statusMap);
    }


}



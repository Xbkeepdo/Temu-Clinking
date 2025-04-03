package com.ch.clinking;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.clinking.entity.*;

import com.ch.clinking.entity.VO.ComputePage;
import com.ch.clinking.entity.VO.ProductVO;
import com.ch.clinking.mapper.*;
import com.ch.clinking.platform.GetProductInfo;
import com.ch.clinking.platform.GetProductState;
import com.ch.clinking.service.*;
import com.ch.clinking.service.scheduler.ProductScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest

@MapperScan("com.ch.clinking.mapper")

class ClinkingApplicationTests {

    private static List<Shop> shopList;
    private static List<User> userList;


    @Resource
    ShopMapper shopMapper;

    @Resource
    MerService merService;

    @Resource
    ProductInfoMapper productInfoMapper;


    @Resource
    ProductMapper productMapper;

    @Resource
    ProductService productService;

    @Resource
    ProductDesignerService  productDesignerService;


    @Resource
    CostService costService;

    @Resource
    ProductCostMapper productCostMapper;

    @Resource
    SalesHistoryService salesHistoryService;

    @Resource
    ProductDesignerMapper productDesignerMapper;
    @Test
    public   void test () throws Exception {





        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setShopType("标码"); // 二店
        CharmHop.setAccessToken("ukpiwfckgcu7ifboozkvs2n167whosh2xlorzqgtfszhcx83fexqqnyr");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");


        Shop Nalim = new Shop();
        Nalim.setShopId("634418218940910");
        Nalim.setShopType("标码");
        Nalim.setAccessToken("iynrezazrim90q1in52zmbefii1g2jcdubytx5ddeoh3u5bnaochdg03");
        Nalim.setAppKey("d422fea5868d0a2da1723c12274c87ec");
        Nalim.setAppSecret("fb75dc07fe580fdd156002b831f6b7144486126a");

        Shop mihu = new Shop();
        mihu.setShopId("634418210011963");
        mihu.setShopType("标码");
        mihu.setAccessToken("8hkyivcvewxuptpjzrsg5cwhrrjstkmsq066ajq8lycwg3vurlz80yn2");
        mihu.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        mihu.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");
        mihu.setShopName("MiiHoo");


        List<Shop> shopList = new ArrayList<>();
//        shopList.add(CharmHop);
//        shopList.add(mihu);
//        shopList.add(Nalim);
        shopList = shopMapper.selectList(new QueryWrapper<Shop>().ne("accessToken", '1'));


        int totalCount = 0;
        int currentPage = 1;

        //max =100
        int pageSize = 100;
        int totalPage = 999;

//
        for (Shop shop : shopList) {
       // 更新商店product状态
           updataShopProductState(shop,currentPage, pageSize);
        //根据product进行数据库更新
//
         }
    }




    @Test
    void testSalesCount() {

        //获取总销量
        List<String> skcIds=productDesignerMapper.searchAllByDesigner("634418212510386","Top");
        double totalSalesCount = salesHistoryService.getTotalSalesCount(skcIds, null, null);

        System.out.println("totalSalesCount:"+totalSalesCount);

    }
    @Test
    void testVoPage(){
        Page<Product> rawPage = productService.getProducts("Top","634418212510386",1,100,null,null);


    //    System.out.println("分页结果："+rawPage.getRecords());


        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        List<ProductVO> voList = rawPage.getRecords().stream().map(product -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);

//            // 处理时间范围
//            LocalDateTime actualStart = startTime != null ? startTime : LocalDateTime.now().minusDays(30);
//            LocalDateTime actualEnd = endTime != null ? endTime : LocalDateTime.now();

            //  计算销售数据
            double salesCount = salesHistoryService.getSalesCountByProduct(
                    product.getSkcId(),
                    startTime,
                    endTime
            );




            vo.setSalesCount(salesCount);
           // vo.setDailySales(dailySales);

            return vo;
        }).collect(Collectors.toList());

      //  System.out.println("volist:"+voList);

        // 12. 构建返回分页
        Page<ProductVO> resultPage = new Page<ProductVO>(rawPage.getCurrent(), rawPage.getSize(), rawPage.getTotal());
       // System.out.println("最终结果前："+resultPage);

        resultPage.setRecords(voList);

       // System.out.println("最终结果"+resultPage.getRecords());





        //获取总销量
        List<String> skcIds=productDesignerMapper.searchAllByDesigner("634418212510386","Top");
        double totalSalesCount = salesHistoryService.getTotalSalesCount(skcIds, null, null);

        System.out.println("totalSalesCount:"+totalSalesCount);



        ComputePage<ProductVO> result = new ComputePage<>();
        result.setPageData(productService.searchTimeConvertProductToVO(rawPage, startTime, endTime));
        result.setTotalSales(totalSalesCount);

        System.out.println("result:========"+result.getPageData());


    }
    @Test
    void testPath(){

        System.out.println(System.getProperty("user.dir"));

    }


    @Test
    void testProductCost(){
        //判断有无成本
        ProductCost productCost = productCostMapper.selectOne(new QueryWrapper<ProductCost>().eq("skcId", "2665016973"));
        if(productCost == null) {

            System.out.println("========================成本为空=============================");
            //插入成本
            ProductCost cost1 = new ProductCost("912030876","2665016973","3");


            productCostMapper.insert(cost1);
        }


    }
    @Test
    void testbind(){
        ProductDesigner productDesigner = new ProductDesigner();
        productDesigner.setName("dada");

        productDesigner.setShopId("dsadsa");
        productDesigner.setShopName("dadada");
        productDesigner.setSkcId("321421421421");
        productDesignerService.bindProduct(productDesigner);
    }

    @Test
    void testUppdate(){


        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("skcId", "1744633671");  // 条件：根据 skcId 更新
        updateWrapper.set("state", "12");  // 设置更新字段
        productService.updateProductBySkcId("1744633671", updateWrapper);

        //  productMapper.updateStateBySkcIdInt("8822240095","12");


    }

    @Test
    void testGetState() throws Exception {

        Shop CharmHop = new Shop();
        CharmHop.setShopId("634418212510386"); // 二店
        CharmHop.setShopType("标码"); // 二店
        CharmHop.setAccessToken("ukpiwfckgcu7ifboozkvs2n167whosh2xlorzqgtfszhcx83fexqqnyr");
        CharmHop.setAppKey("6a8a177e4c51edb61c24f09f01c3a9e9");
        CharmHop.setAppSecret("740020759d9ace02c653cc5236fa81454750368b");

        List<String>  skuList =new ArrayList<>();
            skuList.add("7548755846");

          String productStateJsonDate = GetProductState.getInstance().GetSkcListState(CharmHop, 1, 2,skuList);
          System.out.println(productStateJsonDate);
    }



    public List<Product> getShopProductList(Shop shop)throws Exception{
        int totalCount = 0;
        int currentPage = 1;
        int pageSize = 100;
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



    public  List<Product> getListProductInfo(Map<String, Object> data, Shop shop) throws JSONException {
        List<Product> productList = new ArrayList<>();

        // 获取 'result' 部分
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> producRectList = (List<Map<String, Object>>) result.get("data");

        // 遍历每个产品
        for (Map<String, Object> productRec : producRectList) {

            String skcId = productRec.get("productSkcId").toString();

            Product p = productService.getOne(new QueryWrapper<Product>().eq("skcId", skcId));





















            //数据库存在，也要取数据更新status，如果写下面代码就代表第一次取的是啥样后面依旧是什么样,
            //之前这里有错误，如果continue，他在数据库的状态就会一直不变了。

//            if (p != null) {
//                continue;
//            }


















            // 创建 Product 对象
            Product product = new Product();

            // 提取需要的字段并设置
            product.setShopId(shop.getShopId());
            product.setSpuId(productRec.get("productId").toString());
            product.setSkcId(productRec.get("productSkcId").toString());
            bindingDesigner(productRec.get("productName").toString(), productRec.get("productSkcId").toString(), shop);  // 绑定设计师
            product.setTitle(productRec.get("productName").toString());
            product.setCreateTime((Long) productRec.get("createdAt"));
            Merchandise mer = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", product.getSkcId()));
            if (mer != null){
                product.setPrice(mer.getPrice());
            }









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




            Product exitProduct = productService.getOne(new QueryWrapper<Product>().eq("skcId", product.getSkcId()));
            if (exitProduct == null){
                productMapper.insert(product);
            }
            else{
                productService.update(product, new UpdateWrapper<Product>().eq("skcId", product.getSkcId()));
            }
            //更新productInfo
            ProductInfo exitProductInfo = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("skcId", product.getSkcId()));
            if (exitProductInfo == null){
                //获取成分信息
                ProductInfo productInfo = new ProductInfo();
                productInfo.setMaterial(getMaterialString(productRec));
                productInfo.setSkcId(product.getSkcId());
                productInfo.setSpuId(product.getSpuId());

                productInfoMapper.insert(productInfo);
            }





        }

        // 返回所有的 Product 对象
        return productList;
    }






    private  void bindingDesigner(String productName, String productSkcId, Shop shop) {

//        if (productName.contains("【叶】")) {
//            ProductDesigner productDesigner = new ProductDesigner();
//            productDesigner.setSkcId(productSkcId);
//            productDesigner.setName("Jodie");
//            productDesigner.setShopId(shop.getShopId());
//            productDesigner.setShopName(shop.getShopName());
//            productDesignerService.saveOrUpdate(productDesigner);
//        }
        if (productName.contains("招财定向")) {
            ProductDesigner productDesigner = new ProductDesigner();
            productDesigner.setSkcId(productSkcId);
            productDesigner.setName("林招财");
            productDesigner.setShopId(shop.getShopId());
            productDesigner.setShopName(shop.getShopName());
            productDesignerService.saveOrUpdate(productDesigner);
        }
        if (productName.contains("念念定向") || productName.contains("念念精选")) {
            ProductDesigner productDesigner = new ProductDesigner();
            productDesigner.setSkcId(productSkcId);
            productDesigner.setName("沫瀛唸");
            productDesigner.setShopId(shop.getShopId());
            productDesigner.setShopName(shop.getShopName());
            productDesignerService.saveOrUpdate(productDesigner);
        }

    }
    //更新商品状态

    public  void updataShopProductState(Shop shop,int page,int pagesize) throws Exception {

        List<Product> productList = getShopProductList(shop);
        List<String>  skuList =new ArrayList<>();
        Map<String,String> statusMap = new HashMap<>();
        //获取所有商品的代表性skuid,SKUlist最大长度100

        for( Product product : productList){

            skuList.add(product.getsSku());
            System.out.println("==================="+product.getSkcId());
        }

        System.out.println("ProductFinished=======================================");

        // 将 skuList 分批处理，每批最多 100 个 sku
        int batchSize = 100;
        for (int i = 0; i < skuList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, skuList.size());
            List<String> skuBatch = skuList.subList(i, end);


            //获取产品生命周期信息
            String productStateJsonDate = GetProductState.getInstance().GetSkcListState(shop, page, pagesize, skuBatch);

            System.out.println("FetStateFinished==================================================");

            //json数据映射为MAP
            SkcToStatusMapping(productStateJsonDate, statusMap);


            //根据map更新product
            for (Product product : productList) {
                product.setState(statusMap.get(product.getSkcId()));
                System.out.println(product.getSkcId() + " = " + product.getState());


                try {
                    //skcid不存在就插入，存在就更新state
                   // LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<Product>().eq(Product::getSkcId, product.getSkcId());
                    Product exitProduct = productMapper.selectOne(new QueryWrapper<Product>().eq("skcId", product.getSkcId()));

                    System.out.println("===============================" + exitProduct + "============================");


                    System.out.println("=======================开始判断成本========================");

                   // 判断有无成本
                    ProductCost productCost = productCostMapper.selectOne(new QueryWrapper<ProductCost>().eq("skcId", product.getSkcId()));
                    System.out.println("============================="+productCost+"=========================================");

                    if(productCost == null) {

                        System.out.println("========================成本为空=============================");
                        //插入成本
                        ProductCost cost1 = new ProductCost(product.getSpuId(), product.getSkcId(), product.getShopId());
                        cost1.setFreight(0.24);
                        cost1.setSack(0.32);
                        cost1.setTotal(0.24 + 0.32);

                        productCostMapper.insert(cost1);
                    }

                    //绑定TOP
                    ProductDesigner exitProductDesigner = productDesignerMapper.selectOne(new QueryWrapper<ProductDesigner>()
                            .eq("skcId", product.getSkcId())
                            .eq("name","Top"));

                    if (exitProductDesigner == null){
                        ProductDesigner productDesigner = new ProductDesigner();
                        productDesigner.setSkcId(product.getSkcId());
                        productDesigner.setShopId(shop.getShopId());
                        productDesigner.setName("Top");
                        productDesigner.setShopName(shop.getShopName());

                        productDesignerMapper.insert(productDesigner);
                    }





                    if (exitProduct == null) {
                        productMapper.insert(product);
                    } else {


                        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("skcId", product.getSkcId())
                                .set("state", product.getState());  // 条件：根据 skcId 更新
                       // updateWrapper.set("state", product.getState());  // 设置更新字段
                        productService.update(null, updateWrapper);

                        // productMapper.updateStateBySkcIdInt(product.getSkcId(),product.getState());
                    }
                } catch (Exception e) {

                    e.printStackTrace();  // 打印日志以调试错误
                    //   throw e;  // 或者重新抛出异常，触发事务回滚
                }


            }

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


    private String getMaterialString(Map<String, Object> productRec) throws JSONException {
        // 提取成分信息逻辑
        StringBuilder materialBuilder = new StringBuilder();

// 1. 获取 productProperties 列表（注意类型转换）
        List<Map<String, Object>> propertiesList = (List<Map<String, Object>>) productRec.get("productProperties");

// 2. 遍历所有属性
        if (propertiesList != null) {
            for (Map<String, Object> property : propertiesList) {
                // 检查是否是"成分"属性
                if ("成分".equals(property.getOrDefault("propName", "").toString())) {
                    // 安全获取值（处理可能为null的情况）
                    String numberInputValue = property.containsKey("numberInputValue") ?
                            property.get("numberInputValue").toString() : "";

                    String valueUnit = property.containsKey("valueUnit") ?
                            property.get("valueUnit").toString() : "";

                    String propValue = property.containsKey("propValue") ?
                            property.get("propValue").toString() : "";

                    // 拼接字符串
                    materialBuilder
                            .append(numberInputValue)
                            .append(valueUnit)
                            .append(propValue)
                            .append("；"); // 添加中文分号分隔符
                }
            }
        }

// 3. 去除最后一个分号（如果存在）
        if (materialBuilder.length() > 0) {
            materialBuilder.setLength(materialBuilder.length() - 1);
        }

        return materialBuilder.toString();
    }

}

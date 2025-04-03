package com.ch.clinking.service.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ch.clinking.entity.*;
import com.ch.clinking.mapper.ProductCostMapper;
import com.ch.clinking.mapper.ProductInfoMapper;
import com.ch.clinking.mapper.ProductMapper;
import com.ch.clinking.platform.GetProductInfo;
import com.ch.clinking.platform.GetProductState;
import com.ch.clinking.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ProductScheduler {

    private static List<Shop> shopList;
    private static List<User> userList;

    @Resource
    private  ShopService shopService;
    @Resource
    private UserService userService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private  ProductDesignerService productDesignerService;

    @Resource
    private  ProductService productService;

    @Resource
    private  MerService merService;

    @Resource
    private  CostService costService;
    @Resource
    private DesignerService designerService;

    @Resource
    ProductCostMapper productCostMapper;

    @Resource
    ProductInfoMapper productInfoMapper;

    @PostConstruct
    public void schedulerDo() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            // 这里写保存数据的逻辑
            try {
                getProductInfo();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // 计算下一次执行的时间
        long initialDelay = calculateInitialDelay();
        long period = TimeUnit.DAYS.toMillis(1); // 每天执行一次

        // 安排任务
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private static long calculateInitialDelay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 30);
        calendar.set(Calendar.MILLISECOND, 0);

        Date nextExecutionTime = calendar.getTime();
        if (nextExecutionTime.before(new Date())) {
            // 如果今天的23:00已过，则设为明天的23:00
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            nextExecutionTime = calendar.getTime();
        }

        return nextExecutionTime.getTime() - System.currentTimeMillis();
    }



    public void getProductInfo() throws Exception {

              shopList =  shopService.list(new QueryWrapper<Shop>().ne("accessToken", '1'));

            for (Shop shop : shopList) {
                //更新
                    updataShopProductState(shop, 1, 100);
                //根据Product进行更新 updata（product）
            }

    }



    //获取一个店铺所有商品列表
    public  List<Product> getShopProductList(Shop shop)throws Exception{
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
             System.out.println(productList);

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

    //        String skcId = productRec.get("productSkcId").toString();

     //       Product p = productService.getOne(new QueryWrapper<Product>().eq("skcId", skcId));

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

//            productService.saveOrUpdate(product);

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
        if (productName.contains("招财")) {
            ProductDesigner productDesigner = new ProductDesigner();
            productDesigner.setSkcId(productSkcId);
            productDesigner.setName("林招财");
            productDesigner.setShopId(shop.getShopId());
            productDesigner.setShopName(shop.getShopName());
            productDesignerService.saveOrUpdate(productDesigner);
        }
        if (productName.contains("念念") || productName.contains("NN")) {
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
            System.out.println(product.getSkcId());
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
                    LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<Product>().eq(Product::getSkcId, product.getSkcId());
                    Product exitProduct = productMapper.selectOne(queryWrapper);

                    System.out.println("===============================" + exitProduct + "============================");

                    if (exitProduct == null) {
                        System.out.println("========================成本为空=============================");
                        //插入成本
                        ProductCost cost1 = new ProductCost(product.getSpuId(), product.getSkcId(), product.getShopId());
                        cost1.setFreight(0.24);
                        cost1.setSack(0.32);
                        cost1.setTotal(0.24 + 0.32);

                        productCostMapper.insert(cost1);
//                        }

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
    public  void SkcToStatusMapping(String json, Map<String,String> statusMap) throws JsonProcessingException {
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
        System.out.println("SKC to Status Mapping: " + statusMap);
    }


    public String getMaterialString(Map<String, Object> productRec) throws JSONException {
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

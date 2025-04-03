package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.clinking.entity.*;
import com.ch.clinking.entity.DOtoDTO.MerToMerSaleAndCostDTO;
import com.ch.clinking.entity.DOtoDTO.MerToMerSaleDTO;
import com.ch.clinking.entity.Dto.MerSaleAndCostDTO;
import com.ch.clinking.entity.Dto.MerSaleDTO;
import com.ch.clinking.platform.GetSaleInfo;
import com.ch.clinking.service.*;
import com.ch.clinking.service.impl.ShopServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/mer")
public class MerController {

    @Resource
    private MerService merService;

    @Resource
    private OrderService orderService;

    @Resource
    private SameKindService sameKindService;

    @Resource
    private SaleInfoService saleInfoService;

    @Resource
    private CostService costService;

    @Resource
    private StockLocationsService stockLocationsService;

    @Resource
    private ProductionInfoService productionInfoService;

    @Value("${merImagesFilePath}")
    private String IMAGE_PATH;
    @Autowired
    private ShopServiceImpl shopService;


    public Msg findSwiper(Integer shopId) {
        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId  ", shopId).orderByAsc("createTime"));
        Map<String, Object> map = new HashMap<>();
        map.put("message", merchandiseList);
        return Msg.ok(map);

    }

    /**
     * 查询商品列表
     *
     * @return
     */
    // 子款查找差异化
//    public PageResult<Merchandise> selectById(@RequestParam("shopId") String shopId, @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("showSonMer") int showSonMer) {
    @PostMapping("/selectMerByShopId")
    public PageResult<Merchandise> selectById(@RequestParam("shopId") String shopId, @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
//        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId", shopId).eq("color","花色").orderByDesc("createTime"));

        System.out.println(currentPage);
        System.out.println(pageSize);

        List<Merchandise> allMerchandiseList = merService.list(new QueryWrapper<Merchandise>()
                    .eq("shopId", shopId)
                    .eq("color", "花色")
                    .eq("state", "已上架")
                    .orderByDesc("createTime"));


//        List<Merchandise> allMerchandiseList;
//        if (showSonMer == 0) {
//            // 手动分页逻辑
//            allMerchandiseList = merService.list(new QueryWrapper<Merchandise>()
//                    .eq("shopId", shopId)
//                    .eq("color", "花色")
//                    .eq("state", "已上架")
//                    .orderByDesc("createTime"));
//        } else {
//            // 手动分页逻辑
//            allMerchandiseList = merService.list(new QueryWrapper<Merchandise>()
//                    .eq("shopId", shopId)
//                    .eq("state", "已上架")
//                    .orderByDesc("createTime"));
//        }

        int total = allMerchandiseList.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);

        List<Merchandise> merchandiseList = allMerchandiseList.subList(startIndex, endIndex);


//        for (Merchandise merchandise : merchandiseList) {
////            String spu=merchandise.getSpuId(),skc=merchandise.getSkcId();
//            if (merchandise.getColor().equals("Son")) {
//                continue;
////                SameKind sameKind = sameKindService.getOne(new QueryWrapper<SameKind>().eq("skcId", merchandise.getSkcId()));
////                Merchandise mainMer = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", sameKind.getMainSkcId()));
////                merMainToSon(mainMer, merchandise);
////                merchandise.setColor("Son");
//            }
//            List<Order> orderList = orderService.list(
//                    new QueryWrapper<Order>()
//                            .eq("skcId", merchandise.getSkcId())
//                            .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                            .orderByDesc("createdTime")
//            );
//            merchandise.setOrderList(orderList);
////            merchandise.setSpuId(spu);
////            merchandise.setSkcId(skc);
//        }
        // 使用 Iterator 来遍历和删除
        Iterator<Merchandise> iterator = merchandiseList.iterator();
        while (iterator.hasNext()) {
            Merchandise merchandise = iterator.next();

            if (merchandise.getColor().equals("Son")) {
                merchandise.setTitle("[子]" + merchandise.getTitle());
            }

//            List<Order> orderList = orderService.list(
//                    new QueryWrapper<Order>()
//                            .eq("skcId", merchandise.getSkcId())
//                            .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                            .orderByDesc("createdTime")
//            );


            Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", merchandise.getSkcId()));
            List<StockLocations> stockLocations = stockLocationsService.list(
                    new QueryWrapper<StockLocations>()
                            .eq("skcId", merchandise.getSkcId())
            );
            merchandise.setCost(cost);
            if (stockLocations != null) {
                merchandise.setStockLocations(stockLocations);
            }
//            merchandise.setOrderList(orderList);

        }

        System.out.println("merchandiseList" + merchandiseList);

        // 打印分页信息
        System.out.println("Total Records: " + merchandiseList.size());
        System.out.println("Total Pages: " + totalPages);
        System.out.println("Current Page: " + currentPage);
        System.out.println("Page Size: " + pageSize);
        System.out.println("Total Records Retrieved: " + total);

        // 封装到 PageResult 对象
        return new PageResult<>(merchandiseList, total, totalPages, currentPage);
//        return merchandiseList;
    }

    /**
     * 通过SKC查询商品
     *
     * @return
     */
    @PostMapping("/selectMerBySkcId")
    public Merchandise selectBySckId(@RequestParam("skcId") String skcId) {
        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId).orderByDesc("createTime"));

        System.out.println("merchandise,selectMerBySkcId" + merchandise);
        return merchandise;
    }

    /**
     * 通过SKC查询商品
     *
     * @return
     */
    @PostMapping("/selectMerByProductNum")
    public List<Merchandise> selectMerByProductNum(@RequestParam("num") String productNumber, @RequestParam("shopId") String shopId) {
        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().like("productNumber", productNumber).eq("color", "花色").eq("shopId", shopId).orderByDesc("createTime"));

        Iterator<Merchandise> iterator = merchandiseList.iterator();
        while (iterator.hasNext()) {
            Merchandise merchandise = iterator.next();

//            List<Order> orderList = orderService.list(
//                    new QueryWrapper<Order>()
//                            .eq("skcId", merchandise.getSkcId())
//                            .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                            .orderByDesc("createdTime")
//            );

            Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", merchandise.getSkcId()));

            merchandise.setCost(cost);

            List<StockLocations> stockLocations = stockLocationsService.list(
                    new QueryWrapper<StockLocations>()
                            .eq("skcId", merchandise.getSkcId())
            );
            if (stockLocations != null) {
                merchandise.setStockLocations(stockLocations);
            }

//            merchandise.setOrderList(orderList);

        }


        System.out.println("merchandise,selectMerByProductNum" + merchandiseList);
        System.out.println("Finished");
        return merchandiseList;
    }

    /**
     * 通过SKC查询商品
     *
     * @return
     */
    @PostMapping("/selectMerHaveOrderBySkcId")
    public Merchandise selectMerHaveOrderBySkcId(@RequestParam("skcId") String skcId) {
        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId).orderByDesc("createTime"));

//        List<Order> orderList = orderService.list(
//                new QueryWrapper<Order>()
//                        .eq("skcId", merchandise.getSkcId())
//                        .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                        .orderByDesc("createdTime")
//        );
//        merchandise.setOrderList(orderList);

        Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", skcId));

        merchandise.setCost(cost);

        List<StockLocations> stockLocations = stockLocationsService.list(
                new QueryWrapper<StockLocations>()
                        .eq("skcId", merchandise.getSkcId())
        );
        if (stockLocations != null) {
            merchandise.setStockLocations(stockLocations);
        }

        if (merchandise.getColor().equals("Son")) {
            merchandise.setTitle("[子]"+merchandise.getTitle());
        }


        return merchandise;
    }


//    /**
//     * 添加商品
//     *
//     * @return
//     */
//    @Async
//    @Transactional
//    @RequestMapping("/updateMer")
//    public Msg save(@RequestBody Merchandise merchandise){
////        if(merchandise.getId()==null || merchandise.getId()==-1){
////            int productCount = Math.toIntExact(merService.count());
////            merchandise.setId(productCount + 1);
////            System.out.println(merchandise);
////            merService.save(merchandise);
////        }else{
//        boolean saved = merService.saveOrUpdate(merchandise);
////        }
//
//        System.out.println(saved);
//
//        Merchandise search = merService.getById(merchandise.getId());
//
//        while (!search.equals(merchandise)){
//
//        }
//
//        if (saved) {
//            // 这里可以做一些成功后的后续处理，例如发送通知等
//            return Msg.ok();
//        } else {
//            return Msg.error();
//        }
//    }

    @Transactional
    @PostMapping("/updateMer")
    public Msg save(@RequestBody Merchandise merchandise) throws InterruptedException {
        boolean saved = merService.saveOrUpdate(merchandise);


//        while (merchandise.equals(merService.selectBySkcId(merchandise.getSkcId()))){
//            Thread.sleep(500);
//        }

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新商品失败");
        }
    }


    @Transactional
    @PostMapping("/updateSameKind")
    public Msg save(@RequestBody SameKind sameKind) throws InterruptedException {
        boolean saved = sameKindService.saveOrUpdate(sameKind);


//        while (merchandise.equals(merService.selectBySkcId(merchandise.getSkcId()))){
//            Thread.sleep(500);
//        }

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新同款失败");
        }
    }

    @Transactional
    @PostMapping("/addSonMer")
    public Msg addSonMer(@RequestParam("mainSkc") String mainSkc, @RequestParam("spu") String spu, @RequestParam("skc") String skc, @RequestParam("shopId") String shopId) throws InterruptedException {

        Merchandise mainMer = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", mainSkc));

        Merchandise merchandise = new Merchandise();

        merMainToSon(mainMer, merchandise);
        merchandise.setColor("Son");

        merchandise.setSpuId(spu);
        merchandise.setSkcId(skc);
        merchandise.setShopId(shopId);


        boolean saved = merService.saveOrUpdate(merchandise);


//        while (merchandise.equals(merService.selectBySkcId(merchandise.getSkcId()))){
//            Thread.sleep(500);
//        }

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("录入同款失败");
        }
    }

    /**
     * 通过SKC查询商品
     *
     * @return
     */
    @PostMapping("/selectSameKindListByMainSkcId")
    public List<SameKind> selectByMainSckId(@RequestParam("skcId") String skcId) {
        List<SameKind> sameKinds = sameKindService.list(new QueryWrapper<SameKind>().eq("mainSkcId", skcId));

        System.out.println("sameKinds,selectSameKindListByMainSkcId" + sameKinds);
        return sameKinds;
    }

    @Transactional
    @PostMapping("/createCost")
    public Msg createCost(@RequestBody Cost cost) throws InterruptedException {

        boolean saved = costService.saveOrUpdate(cost);

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新成本失败");
        }

    }

    @Transactional
    @PostMapping("/updateMerCost")
    public Msg updateMerCost(@RequestBody Cost cost) throws InterruptedException {

        boolean saved = costService.saveOrUpdate(cost);
        List<Merchandise> mers = merService.list(new QueryWrapper<Merchandise>().eq("spuId", cost.getSpuId()));

        for (Merchandise mer : mers) {
            if (!mer.getSkcId().equals(cost.getSkcId())) {
                Cost c = new Cost();
                c.setSpuId(cost.getSpuId());
                c.setSkcId(mer.getSkcId());
                c.setShopId(cost.getShopId());
                c.setCloth(cost.getCloth());
                c.setCutInto(cost.getCutInto());
                c.setTailor(cost.getTailor());
                c.setPrinting(cost.getPrinting());
                c.setPack(cost.getPack());
                c.setTotal(cost.getTotal());
                c.setQualityRefundRate(cost.getQualityRefundRate());
                c.setSizeRefundRate(cost.getSizeRefundRate());
                c.setMore(cost.getMore());
                c.setQualityScore(cost.getQualityScore());
                costService.saveOrUpdate(c);
            }
        }


//        while (merchandise.equals(merService.selectBySkcId(merchandise.getSkcId()))){
//            Thread.sleep(500);
//        }

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新成本失败");
        }
    }

    /**
     * 通过SKC查询商品成本信息
     *
     * @return
     */
    @PostMapping("/selectCostBySkcId")
    public Cost selectCostBySkcId(@RequestParam("skcId") String skcId) {
        Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", skcId));

        System.out.println("cost,selectCostBySkcId" + cost);
        return cost;
    }

    @PostMapping("/getProductInfoPDF")
    public Msg getProductInfoPDF(@RequestParam("skcId") String skcId) {
        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId));

        ProductionInfo productionInfo = productionInfoService.getOne(new QueryWrapper<ProductionInfo>().eq("skcId", skcId));

        try {
            String pdfInfo = merService.getProductInfoPDF(merchandise, productionInfo);
            System.out.println("Generated PDF Info: " + pdfInfo);  // 如果有输出，说明方法正常执行
        } catch (Exception e) {
            // 捕获异常并输出详细日志
            e.printStackTrace();  // 打印异常堆栈
            System.out.println("Error occurred while generating PDF: " + e.getMessage());
            return Msg.error("Error occurred: " + e.getMessage());  // 返回错误消息给前端
        }

        return Msg.ok();
    }

    @PostMapping("/uploadImage")
    public Msg uploadImage(@RequestBody ImageRequest imageRequest) {
        try {
            // 解码 Base64 字符串
            byte[] imageBytes = Base64.decodeBase64(imageRequest.getBase64Image());

            // 保存为图片文件
            String filePath = IMAGE_PATH + imageRequest.getName();
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageBytes);
            }

            return Msg.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.error();
        }
    }

    @PostMapping("/selectBySKC")
    public Msg selectBySKC(@RequestParam("skc") String skc) {

        int exist = merService.existsBySkcId(skc);


        System.out.println(exist);

        if (exist > 0) {
            return Msg.error();
        }

        return Msg.ok();
    }

    @PostMapping("/selectBySKCNotInMainSkc")
    public Msg selectBySKCNotInMainSkc(@RequestParam("skc") String skc) {

        int exist = merService.existsBySkcId(skc);
        int existSon = sameKindService.existsBySkcId(skc);


        System.out.println(exist);

        if (exist <= 0 && existSon > 0) {
            return Msg.ok();
        }

        return Msg.error();
    }


    @PostMapping("/selectSameKindBySKC")
    public Msg selectSameKindBySKC(@RequestParam("skcId") String skc) {

        int exist = sameKindService.existsBySkcId(skc);

        System.out.println(exist);

        if (exist > 0) {
            return Msg.error();
        }

        return Msg.ok();
    }


    @PostMapping("/delete")
    public Msg delete(@RequestParam("skc") String skc) {

//        Merchandise mer = merService.selectBySkcId(skc);
//
//        String imagePath = IMAGE_PATH + mer.getFirstImage();

        merService.remove(Wrappers.<Merchandise>query().lambda().eq(Merchandise::getSkcId, skc));

        // 如果有图片路径，则删除对应的图片文件
//        if (IMAGE_PATH != null && !imagePath.isEmpty()) {
//            boolean deleteSuccess = deleteImage(imagePath);
//            if (!deleteSuccess) {
//                return Msg.error("删除商品图片失败");
//            }
//        }

        return Msg.ok();
    }

    @PostMapping("/deleteSameKindBySkcId")
    public Msg deleteSameKindBySkcId(@RequestParam("skcId") String skcId) {

//        Merchandise mer = merService.selectBySkcId(skc);
//
//        String imagePath = IMAGE_PATH + mer.getFirstImage();

        sameKindService.remove(Wrappers.<SameKind>query().lambda().eq(SameKind::getSkcId, skcId));

        // 如果有图片路径，则删除对应的图片文件
//        if (IMAGE_PATH != null && !imagePath.isEmpty()) {
//            boolean deleteSuccess = deleteImage(imagePath);
//            if (!deleteSuccess) {
//                return Msg.error("删除商品图片失败");
//            }
//        }

        return Msg.ok();
    }

    @PostMapping("/getShopProfitInfoByFromDataBase")
    public List<MerSaleDTO> getShopProfitInfoByFromDataBase(@RequestParam("shopId") String shopId) throws Exception {

        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId", shopId).orderByDesc("createTime"));

        List<MerSaleDTO> merSaleDTOList = new ArrayList<>();

        for (Merchandise merchandise : merchandiseList) {
            MerSaleDTO merSaleDTO = MerToMerSaleDTO.getInstance().doToDTO(merchandise);

            List<SaleInfo> saleInfoList = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));

            merSaleDTO.setSaleInfoList(saleInfoList);

            merSaleDTOList.add(merSaleDTO);
        }


        return merSaleDTOList;

    }

    @PostMapping("/getShopProfitInfoByFromDataBaseHaveCost")
    public List<MerSaleAndCostDTO> getShopProfitInfoByFromDataBaseHaveCost(@RequestParam("shopId") String shopId) throws Exception {

        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId", shopId).orderByDesc("createTime"));

        List<MerSaleAndCostDTO> merSaleAndCostDTOList = new ArrayList<>();

        for (Merchandise merchandise : merchandiseList) {
            MerSaleAndCostDTO merSaleAndCostDTO = MerToMerSaleAndCostDTO.getInstance().doToDTO(merchandise);

            List<SaleInfo> saleInfoList = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));

            if (merchandise.getSkcId().equals("9714201312")) {
                System.out.println(saleInfoList);
            }

//            List<SameKind> selectList = sameKindService.list(new QueryWrapper<SameKind>().eq("mainSkcId", merchandise.getSkcId()).eq("shopId", merchandise.getShopId()));
//            // 尺寸列表
//            String[] sizes = {"-XXS", "-XS", "-S", "-M", "-L", "-XL", "-XXL", "-0XL", "-1XL", "-2XL", "-3XL", "-4XL", "-5XL", "-6XL"};
//
//            if (!selectList.isEmpty()) {
//                for (SameKind sameKind : selectList) {
//                    List<SaleInfo> sameKindSaleInfoList = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", sameKind.getSkcId()));
//                    for (SaleInfo mainSaleInfo : saleInfoList) {
//                        for (SaleInfo sonSaleInfo : sameKindSaleInfoList) {
//
//                            // 遍历尺寸列表，匹配相应的尺寸
//                            for (String size : sizes) {
//                                if (mainSaleInfo.getClassName().contains(size) && sonSaleInfo.getClassName().contains(size)) {
//                                    mainSaleInfo.setOnSalesDurationOffline(mainSaleInfo.getOnSalesDurationOffline() + sonSaleInfo.getOnSalesDurationOffline());
//                                    mainSaleInfo.setTodaySaleVolume(mainSaleInfo.getTodaySaleVolume() + sonSaleInfo.getTodaySaleVolume());
//                                    mainSaleInfo.setLastSevenDaysSaleVolume(mainSaleInfo.getLastSevenDaysSaleVolume() + sonSaleInfo.getLastSevenDaysSaleVolume());
//                                    mainSaleInfo.setLastThirtyDaysSaleVolume(mainSaleInfo.getLastThirtyDaysSaleVolume() + sonSaleInfo.getLastThirtyDaysSaleVolume());
//                                    mainSaleInfo.setWarehouseInventoryNum(mainSaleInfo.getWarehouseInventoryNum() + sonSaleInfo.getWarehouseInventoryNum());
//                                    mainSaleInfo.setWaitOnShelfNum(mainSaleInfo.getWaitOnShelfNum() + sonSaleInfo.getWaitOnShelfNum());
//                                    mainSaleInfo.setWaitReceiveNum(mainSaleInfo.getWaitReceiveNum() + sonSaleInfo.getWaitReceiveNum());
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }

            // 找子SKC
            SameKind findSon = sameKindService.getOne(new QueryWrapper<SameKind>().eq("skcId", merchandise.getSkcId()));

            String costSKC = merchandise.getSkcId();
            if (findSon != null) {
                costSKC = findSon.getMainSkcId();
            }

            Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", costSKC));

            merSaleAndCostDTO.setSaleInfoList(saleInfoList);

            merSaleAndCostDTO.setCost(cost);

            merSaleAndCostDTOList.add(merSaleAndCostDTO);
        }


        return merSaleAndCostDTOList;

    }

    @PostMapping("/getOneProductionInfoBySkcId")
    public ProductionInfo getOneProductionInfoBySkcId(@RequestParam("skcId") String skcId) {
        ProductionInfo productionInfo = productionInfoService.getOne(new QueryWrapper<ProductionInfo>().eq("skcId", skcId));
        return productionInfo;
    }

    @PostMapping("/getMaterial")
    public String getMaterial(@RequestParam("skcId") String skcId, @RequestParam("shopId") String shopId) throws Exception {
        Shop shop = shopService.getOne(new QueryWrapper<Shop>().eq("shopId", shopId));
        String jsonResponse = GetSaleInfo.getInstance().GetOneProductInfoBySkc(shop, skcId);
        return getMaterialString(jsonResponse);
    }

    private String getMaterialString(String jsonResponse) throws JSONException {
        // 解析 JSON 数据
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray productArray = jsonObject.getJSONObject("result").getJSONArray("data");
        StringBuilder result = new StringBuilder();

        // 遍历每个产品
        for (int i = 0; i < productArray.length(); i++) {
            JSONArray productProperties = productArray.getJSONObject(i).getJSONArray("productProperties");

            // 遍历每个产品属性
            for (int j = 0; j < productProperties.length(); j++) {
                JSONObject property = productProperties.getJSONObject(j);
                if (property.getString("propName").equals("成分")) {
                    String numberInputValue = property.optString("numberInputValue", "");
                    String valueUnit = property.optString("valueUnit", "");
                    String propValue = property.optString("propValue", "");

                    // 拼接字符串
                    result.append(numberInputValue).append(valueUnit).append(propValue).append("；");
                }
            }
        }

        // 去掉最后一个“；”
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }

        // 输出结果
        System.out.println(result.toString());
        return result.toString();
    }


    // 删除图片的方法
    private boolean deleteImage(String imagePath) {
        // 根据图片路径执行删除逻辑，这里是示例代码，具体实现根据你的存储方式来
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            return imageFile.delete();
        } else {
            return false; // 文件不存在或删除失败
        }
    }

    private void merMainToSon(Merchandise mainMer, Merchandise merchandise) {
        // 将 mainMer 的属性赋值给 merchandise

        merchandise.setShopId(mainMer.getShopId());
        merchandise.setSpuId(mainMer.getSpuId());
        merchandise.setSkcId(mainMer.getSkcId());
        merchandise.setProductNumber(mainMer.getProductNumber());
        merchandise.setTitle(mainMer.getTitle());
        merchandise.setFirstImage(mainMer.getFirstImage());
        merchandise.setColor(mainMer.getColor());
        merchandise.setState(mainMer.getState());
        merchandise.setPrice(mainMer.getPrice());
        merchandise.setCreateTime(mainMer.getCreateTime());
        merchandise.setShopType(mainMer.getShopType());
        merchandise.setSizeInfo(mainMer.getSizeInfo());
        merchandise.setWeight(mainMer.getWeight());

        // 复制库存相关属性
        merchandise.setInHouseStock_XXS(mainMer.getInHouseStock_XXS());
        merchandise.setInHouseStock_XS(mainMer.getInHouseStock_XS());
        merchandise.setInHouseStock_S(mainMer.getInHouseStock_S());
        merchandise.setInHouseStock_M(mainMer.getInHouseStock_M());
        merchandise.setInHouseStock_L(mainMer.getInHouseStock_L());
        merchandise.setInHouseStock_XL(mainMer.getInHouseStock_XL());
        merchandise.setInHouseStock_XXL(mainMer.getInHouseStock_XXL());

        merchandise.setInHouseStock_Plus_0XL(mainMer.getInHouseStock_Plus_0XL());
        merchandise.setInHouseStock_Plus_1XL(mainMer.getInHouseStock_Plus_1XL());
        merchandise.setInHouseStock_Plus_2XL(mainMer.getInHouseStock_Plus_2XL());
        merchandise.setInHouseStock_Plus_3XL(mainMer.getInHouseStock_Plus_3XL());
        merchandise.setInHouseStock_Plus_4XL(mainMer.getInHouseStock_Plus_4XL());
        merchandise.setInHouseStock_Plus_5XL(mainMer.getInHouseStock_Plus_5XL());
        merchandise.setInHouseStock_Plus_6XL(mainMer.getInHouseStock_Plus_6XL());

        // 复制平台库存相关属性
        merchandise.setPlatformStock_XXS(mainMer.getPlatformStock_XXS());
        merchandise.setPlatformStock_XS(mainMer.getPlatformStock_XS());
        merchandise.setPlatformStock_S(mainMer.getPlatformStock_S());
        merchandise.setPlatformStock_M(mainMer.getPlatformStock_M());
        merchandise.setPlatformStock_L(mainMer.getPlatformStock_L());
        merchandise.setPlatformStock_XL(mainMer.getPlatformStock_XL());
        merchandise.setPlatformStock_XXL(mainMer.getPlatformStock_XXL());

        merchandise.setPlatformStock_Plus_0XL(mainMer.getPlatformStock_Plus_0XL());
        merchandise.setPlatformStock_Plus_1XL(mainMer.getPlatformStock_Plus_1XL());
        merchandise.setPlatformStock_Plus_2XL(mainMer.getPlatformStock_Plus_2XL());
        merchandise.setPlatformStock_Plus_3XL(mainMer.getPlatformStock_Plus_3XL());
        merchandise.setPlatformStock_Plus_4XL(mainMer.getPlatformStock_Plus_4XL());
        merchandise.setPlatformStock_Plus_5XL(mainMer.getPlatformStock_Plus_5XL());
        merchandise.setPlatformStock_Plus_6XL(mainMer.getPlatformStock_Plus_6XL());

        // 复制激活状态
        merchandise.setActivate_XXS(mainMer.isActivate_XXS());
        merchandise.setActivate_XS(mainMer.isActivate_XS());
        merchandise.setActivate_S(mainMer.isActivate_S());
        merchandise.setActivate_M(mainMer.isActivate_M());
        merchandise.setActivate_L(mainMer.isActivate_L());
        merchandise.setActivate_XL(mainMer.isActivate_XL());
        merchandise.setActivate_XXL(mainMer.isActivate_XXL());

        merchandise.setActivate_Plus_0XL(mainMer.isActivate_Plus_0XL());
        merchandise.setActivate_Plus_1XL(mainMer.isActivate_Plus_1XL());
        merchandise.setActivate_Plus_2XL(mainMer.isActivate_Plus_2XL());
        merchandise.setActivate_Plus_3XL(mainMer.isActivate_Plus_3XL());
        merchandise.setActivate_Plus_4XL(mainMer.isActivate_Plus_4XL());
        merchandise.setActivate_Plus_5XL(mainMer.isActivate_Plus_5XL());
        merchandise.setActivate_Plus_6XL(mainMer.isActivate_Plus_6XL());
    }


}

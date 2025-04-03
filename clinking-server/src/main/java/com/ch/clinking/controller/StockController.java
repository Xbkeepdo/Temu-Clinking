package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ch.clinking.entity.*;
import com.ch.clinking.entity.DOtoDTO.MerToMerSaleDTO;
import com.ch.clinking.entity.Dto.MerSaleDTO;
import com.ch.clinking.entity.Dto.MerSaleHistorySkuDTO;
import com.ch.clinking.service.*;
import com.ch.clinking.service.impl.StockLocationsServiceImpl;
import com.ch.clinking.service.impl.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private StockChangedService stockChangedService;

    @Resource
    private SaleInfoService saleInfoService;

    @Resource
    private MerService merService;

    @Resource
    private OrderService orderService;

    @Resource
    private SameKindService sameKindService;

    @Resource
    private ProductionInfoService productionInfoService;


    @Autowired
    private ShopServiceImpl shopService;
    @Autowired
    private StockLocationsServiceImpl stockLocationsService;


    @Transactional
    @PostMapping("/stockChangedRecord")
    public Msg save(@RequestBody StockChanged stockChanged) throws InterruptedException {
        boolean saved = stockChangedService.saveOrUpdate(stockChanged);

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存库存记录失败");
        }
    }

    /**
     * 通过SKC查询近50条更变记录
     *
     * @return
     */
    @PostMapping("/selectStockChangedListBySkcId")
    public List<StockChanged> selectBySckId(@RequestParam("skcId")String skcId) {
        List<StockChanged> stockList = stockChangedService.list(
                new QueryWrapper<StockChanged>()
                        .eq("skcId", skcId)
                        .orderByDesc("createTime")
                        .last("LIMIT 50")
        );

        System.out.println("stockChangedList,selectStockChangedListBySkcId" + stockList);
        return stockList;
    }

    /**
     * 通过SKC查询销售数据和平台库存
     *  因为现在从数据库查找数据，所以注释掉这段代码
     * @return
     */
//    @PostMapping("/getOneSaleInfoBySkcId")
//    public MerSaleDTO getOneSaleInfoBySkcId(@RequestParam("accessToken")String accessToken, @RequestParam("skcId")String skcId) throws Exception {
//
//        Shop shop = shopService.getOne(new QueryWrapper<Shop>()
//                .eq("accessToken", accessToken)
//        );
//
//        String saleJsonDate = GetSaleInfo.getInstance().GetOneSaleInfoBySkc(shop, skcId);
//        System.out.println("accessToken:"+accessToken);
//        System.out.println("skcId:"+skcId);
//
//        // 解析 JSON 数据
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> data = objectMapper.readValue(saleJsonDate, Map.class);
//
//        // 调用 process 方法处理数据
//        SaleInfo saleInfoList = getOneSaleProcess(data, skcId);
//
//        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId));
//
//        MerSaleDTO merSaleDTO = MerToMerSaleDTO.getInstance().doToDTO(merchandise);
//
//        List<SaleInfo> saleInfoListDataBase = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));
//
//        merSaleDTO.setSaleInfoList(saleInfoListDataBase);
//
//        List<Order> orderList = orderService.list(
//                new QueryWrapper<Order>()
//                        .eq("skcId", merchandise.getSkcId())
//                        .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                        .orderByDesc("createdTime")
//        );
//        merSaleDTO.setOrderList(orderList);
//
//        return merSaleDTO;
//    }

//    /**
//     * 通过SKC查询销售数据和平台库存
//     * 因为现在从数据库获取数据，所以注释此代码
//     * @return
//     */
//    @PostMapping("/getShopSaleInfoBySkcId")
//    public List<MerSaleDTO> getShopSaleInfoBySkcId(@RequestParam("accessToken")String accessToken, @RequestParam("shopId")String shopId) throws Exception {
//
//        List<String> skcIdList = merService.selectAllSkcId(shopId);
//        System.out.println("accessToken:"+accessToken);
//
//        Shop shop = shopService.selectByShopId(shopId);
//
////        return skcIdList;
//
//        String saleJsonDate = GetSaleInfo.getInstance().GetListSaleInfoBySkc(shop, skcIdList);
//
////        return saleJsonDate;
//
////        // 解析 JSON 数据
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> data = objectMapper.readValue(saleJsonDate, Map.class);
//
//        // 调用 process 方法处理数据
//        List<SaleInfo> saleInfoListGet = getListSaleProcess(data, skcIdList);
//
//        List<Merchandise> merchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId", shopId).orderByDesc("createTime"));
//
//        List<MerSaleDTO> merSaleDTOList = new ArrayList<>();
//
//        for (Merchandise merchandise : merchandiseList) {
//            MerSaleDTO merSaleDTO = MerToMerSaleDTO.getInstance().doToDTO(merchandise);
//
//            List<SaleInfo> saleInfoList = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));
//
//            merSaleDTO.setSaleInfoList(saleInfoList);
//
//            List<Order> orderList = orderService.list(
//                    new QueryWrapper<Order>()
//                            .eq("skcId", merchandise.getSkcId())
//                            .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
//                            .orderByDesc("createdTime")
//            );
//            merSaleDTO.setOrderList(orderList);
//
//            merSaleDTOList.add(merSaleDTO);
//        }
//
//
//        return merSaleDTOList;
//
//    }



    /**
     * 通过SKC查询销售数据和平台库存
     *
     * @return
     */
    @PostMapping("/getOneAutoMakeOrderBySkcId")
    public Msg getOneAutoMakeOrderBySkcId(@RequestParam("skcId")String skcId) throws Exception {


        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId));

        MerSaleDTO merSaleDTO = MerToMerSaleDTO.getInstance().doToDTO(merchandise);

        List<SaleInfo> saleInfoListDataBase = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));

        merSaleDTO.setSaleInfoList(saleInfoListDataBase);

        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("skcId", merchandise.getSkcId())
                        .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
                        .orderByDesc("createdTime")
        );
        merSaleDTO.setOrderList(orderList);

        Map<String, Object> map = new HashMap<>();
        map.put("message", saleInfoService.autoMakeOrderOneSkc(merSaleDTO));

        return Msg.ok(map);
    }

    /**
     * 通过SKC查询销售数据和平台库存
     *
     * @return
     */
    @PostMapping("/getOneSaleInfoBySkcIdFromDataBase")
    public MerSaleDTO getOneSaleInfoBySkcIdFromDataBase(@RequestParam("skcId")String skcId) {

        // 找子SKC
        SameKind findSon = sameKindService.getOne(new QueryWrapper<SameKind>().eq("skcId", skcId));

        // 找到此产品
        Merchandise merchandise = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", skcId));

        // 获取主SKC
        String mainSkcId = merchandise.getSkcId();
        if (findSon != null) {
            mainSkcId = findSon.getMainSkcId();

            Merchandise merMain = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", findSon.getMainSkcId()));

            // 将主SKC的值全部赋值给子SKC（除了SKC值）
            merMainToSon(merMain, merchandise);
            merchandise.setColor("Son");
            merchandise.setTitle("[子]" + merchandise.getTitle());
            merchandise.setSkcId(skcId);
        }


        // 转换类型
        MerSaleDTO merSaleDTO = MerToMerSaleDTO.getInstance().doToDTO(merchandise);


        // 找到此产品的销售数据
        List<SaleInfo> saleInfoListDataBase = saleInfoService.list(new QueryWrapper<SaleInfo>().eq("skcId", merchandise.getSkcId()));

        // 设置销售数据
        merSaleDTO.setSaleInfoList(saleInfoListDataBase);



        // 查主产品的产品信息并设置
        ProductionInfo productionInfo = productionInfoService.getOne(new QueryWrapper<ProductionInfo>().eq("skcId", mainSkcId));
        if (productionInfo != null) {
            merSaleDTO.setProductionInfo(productionInfo);
        }

        // 查主产品的下单情况并设置
        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("skcId", mainSkcId)
                        .and(wrapper -> wrapper.eq("state", "待出货").or().eq("state", "未下单"))
                        .orderByDesc("createdTime")
        );
        merSaleDTO.setOrderList(orderList);

        return merSaleDTO;
    }



    @PostMapping("/selectMerLocationsBySkcId")
    public List<StockLocations> selectMerLocationsBySkcId(@RequestParam("skcId")String skcId) throws Exception {


        List<StockLocations> stockLocations = stockLocationsService.list(new QueryWrapper<StockLocations>().eq("skcId", skcId));

        System.out.println("merStockLocations,selectMerLocationsBySkcId" + stockLocations);
        return stockLocations;
    }

    @PostMapping("/deleteStockLocationsById")
    public Msg deleteStockLocationsById(@RequestParam("id") String id) {

        stockLocationsService.remove(Wrappers.<StockLocations>query().lambda().eq(StockLocations::getId, id));

        return Msg.ok();
    }

    @Transactional
    @PostMapping("/updateStockLocation")
    public Msg save(@RequestBody StockLocations stockLocations) throws InterruptedException {
        boolean saved = stockLocationsService.saveOrUpdate(stockLocations);

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新位置失败");
        }
    }

    public SaleInfo getOneSaleProcess(Map<String, Object> data, String targetSkcId) {
        SaleInfo _saleInfo = null;

        // 获取 result 里的 subOrderList
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> subOrderList = (List<Map<String, Object>>) result.get("subOrderList");

        for (Map<String, Object> subOrder : subOrderList) {
            // 先核对 productSkcId
            Long productSkcId = Long.parseLong(subOrder.get("productSkcId").toString());
            // 获取 mark 的值
            double mark = Double.parseDouble(subOrder.get("mark").toString());

            // 保留两位小数
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedMark = df.format(mark);
            if (!productSkcId.toString().equals(targetSkcId)) {
                continue;
            }


            // 获取 skuQuantityDetailList
            List<Map<String, Object>> skuQuantityDetailList = (List<Map<String, Object>>) subOrder.get("skuQuantityDetailList");

            // 遍历 skuQuantityDetailList
            for (Map<String, Object> skuDetail : skuQuantityDetailList) {
                SaleInfo saleInfo = new SaleInfo();
                saleInfo.setSkcId(targetSkcId);
                saleInfo.setMark(Double.parseDouble(formattedMark));
                saleInfo.setClassName(skuDetail.get("className").toString());
                Map<String, Integer> sizeMap = new HashMap<>();
                sizeMap.put("XXS", 1);
                sizeMap.put("XS", 2);
                sizeMap.put("S", 3);
                sizeMap.put("M", 4);
                sizeMap.put("L", 5);
                sizeMap.put("XL", 6);
                sizeMap.put("XXL", 7);
                sizeMap.put("0XL", 8);
                sizeMap.put("1XL", 9);
                sizeMap.put("2XL", 10);
                sizeMap.put("3XL", 11);
                sizeMap.put("4XL", 12);
                sizeMap.put("5XL", 13);
                sizeMap.put("6XL", 14);

                for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
                    if (saleInfo.getClassName().contains(entry.getKey())) {
                        saleInfo.setSkuSizeNum(entry.getValue());
                        break;  // 找到匹配的值后可以跳出循环
                    }
                }


                Long productSkuId = Long.parseLong(skuDetail.get("productSkuId").toString());
                saleInfo.setSkuId(productSkuId.toString());
                saleInfo.setOnSalesDurationOffline((Integer) subOrder.get("onSalesDurationOffline"));
                saleInfo.setTodaySaleVolume((Integer) skuDetail.get("todaySaleVolume"));
                saleInfo.setLastSevenDaysSaleVolume((Integer) skuDetail.get("lastSevenDaysSaleVolume"));
                saleInfo.setLastThirtyDaysSaleVolume((Integer) skuDetail.get("lastThirtyDaysSaleVolume"));

                Map<String, Object> inventoryNumInfo = (Map<String, Object>) skuDetail.get("inventoryNumInfo");
                saleInfo.setWarehouseInventoryNum((Integer) inventoryNumInfo.get("warehouseInventoryNum"));
                saleInfo.setWaitOnShelfNum((Integer) inventoryNumInfo.get("waitOnShelfNum"));
                saleInfo.setWaitReceiveNum((Integer) inventoryNumInfo.get("waitReceiveNum"));

                _saleInfo = saleInfo;
                saleInfoService.saveOrUpdate(saleInfo);
            }

        }
        return _saleInfo;
    }

    public List<SaleInfo> getListSaleProcess(Map<String, Object> data, List<String> skcIdList) {
        List<SaleInfo> saleInfoList = new ArrayList<>();

        // 获取 result 里的 subOrderList
        Map<String, Object> result = (Map<String, Object>) data.get("result");
        List<Map<String, Object>> subOrderList = (List<Map<String, Object>>) result.get("subOrderList");

        for (Map<String, Object> subOrder : subOrderList) {
            // 先核对 productSkcId
            Long productSkcId = Long.parseLong(subOrder.get("productSkcId").toString());
            // 获取 mark 的值
            double mark = Double.parseDouble(subOrder.get("mark").toString());

            // 保留两位小数
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedMark = df.format(mark);
            if (!skcIdList.contains(productSkcId.toString())) {
                System.out.println("此SKC不在本地系统中！"+productSkcId);
                continue;
            }

            // 获取 skuQuantityDetailList
            List<Map<String, Object>> skuQuantityDetailList = (List<Map<String, Object>>) subOrder.get("skuQuantityDetailList");

            // 遍历 skuQuantityDetailList
            for (Map<String, Object> skuDetail : skuQuantityDetailList) {
                SaleInfo saleInfo = new SaleInfo();
                saleInfo.setSkcId(productSkcId.toString());
                saleInfo.setMark(Double.parseDouble(formattedMark));
                saleInfo.setClassName(skuDetail.get("className").toString());
                Map<String, Integer> sizeMap = new HashMap<>();
                sizeMap.put("XXS", 1);
                sizeMap.put("XS", 2);
                sizeMap.put("S", 3);
                sizeMap.put("M", 4);
                sizeMap.put("L", 5);
                sizeMap.put("XL", 6);
                sizeMap.put("XXL", 7);
                sizeMap.put("0XL", 8);
                sizeMap.put("1XL", 9);
                sizeMap.put("2XL", 10);
                sizeMap.put("3XL", 11);
                sizeMap.put("4XL", 12);
                sizeMap.put("5XL", 13);
                sizeMap.put("6XL", 14);

                for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
                    if (saleInfo.getClassName().contains(entry.getKey())) {
                        saleInfo.setSkuSizeNum(entry.getValue());
                        break;  // 找到匹配的值后可以跳出循环
                    }
                }

                Long productSkuId = Long.parseLong(skuDetail.get("productSkuId").toString());
                saleInfo.setSkuId(productSkuId.toString());
                saleInfo.setOnSalesDurationOffline((Integer) subOrder.get("onSalesDurationOffline"));
                saleInfo.setTodaySaleVolume((Integer) skuDetail.get("todaySaleVolume"));
                saleInfo.setLastSevenDaysSaleVolume((Integer) skuDetail.get("lastSevenDaysSaleVolume"));
                saleInfo.setLastThirtyDaysSaleVolume((Integer) skuDetail.get("lastThirtyDaysSaleVolume"));

                Map<String, Object> inventoryNumInfo = (Map<String, Object>) skuDetail.get("inventoryNumInfo");
                saleInfo.setWarehouseInventoryNum((Integer) inventoryNumInfo.get("warehouseInventoryNum"));
                saleInfo.setWaitOnShelfNum((Integer) inventoryNumInfo.get("waitOnShelfNum"));
                saleInfo.setWaitReceiveNum((Integer) inventoryNumInfo.get("waitReceiveNum"));

                saleInfoList.add(saleInfo);
                saleInfoService.saveOrUpdate(saleInfo);
            }

        }
        return saleInfoList;
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

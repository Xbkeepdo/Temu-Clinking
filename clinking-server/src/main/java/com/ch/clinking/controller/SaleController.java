package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.clinking.entity.*;
import com.ch.clinking.entity.DOtoDTO.MerToMerSaleAndCostDTO;
import com.ch.clinking.entity.DOtoDTO.MerToMerSaleHistoryShopProfitDTO;
import com.ch.clinking.entity.Dto.MerSaleAndCostDTO;
import com.ch.clinking.entity.Dto.MerSaleHistoryShopProfitDTO;
import com.ch.clinking.service.*;
import com.ch.clinking.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Resource
    private MerService merService;

    @Resource
    private ProductionInfoService productionInfoService;

    @Resource
    private CostService costService;

    @Resource
    private SalesHistoryService salesHistoryService;

    @Resource
    private SalesHistorySKUService salesHistorySKUService;

    @Resource
    private SameKindService sameKindService;

//    @GetMapping("/getSalesCountByProduct")
//    public int getSalesCountByProduct(@RequestParam String skcId) {
//
//        int salesCount = salesHistoryService.getSalesCountByProduct(skcId);
//
//        Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", skcId));
//
//        if (cost == null) {
//            return salesCount;
//        }
//
//        double realCount = (100 - cost.getQualityRefundRate()) * 0.01 * salesCount;
//
//        // 通过 skcId  查询该商品的销售件数
//        return (int)realCount;
//    }

    @PostMapping("/getSalesHistory")
    public List<SalesHistory> getSalesHistory(@RequestParam("skcId") String skcId) {
        List<SalesHistory> salesHistories = salesHistoryService.list(new QueryWrapper<SalesHistory>().eq("skcId", skcId).orderByDesc("salesDate").last("LIMIT 15"));

        // 反转列表
        Collections.reverse(salesHistories);

        Date firstSalesDate = null;
        SalesHistory firstSalesHistories = null;
        if (!salesHistories.isEmpty()) {
            firstSalesDate = salesHistories.get(0).getSalesDate();
            firstSalesHistories = salesHistories.get(salesHistories.size() - 1);
        } else {
            firstSalesDate = DateUtil.getFormatTime();
            SalesHistory noSalesHistory = new SalesHistory();
            noSalesHistory.setSkcId(skcId);
            noSalesHistory.setSalesDate(firstSalesDate);
            noSalesHistory.setCreatedTime(firstSalesDate);
            noSalesHistory.setSalesCount(0);
            noSalesHistory.setOnSalesDurationOffline(0);
            noSalesHistory.setTotalSaleVolume(0);
            noSalesHistory.setInCardNumber(0);
            noSalesHistory.setInCartNumber7d(0);
            // 将新对象插入到列表的最前面
            salesHistories.add(0, noSalesHistory);
            return salesHistories;
        }

        while (salesHistories.size() < 15) {
            SalesHistory newSalesHistory = new SalesHistory();

            // 设置 salesDate 为第一个值的前一天
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstSalesDate);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            newSalesHistory.setSalesDate(calendar.getTime());

            // 设置其他必要字段
            newSalesHistory.setSkcId(skcId);
            newSalesHistory.setSalesCount(0);
            newSalesHistory.setCreatedTime(calendar.getTime());
            newSalesHistory.setTotalSaleVolume(firstSalesHistories.getTotalSaleVolume());
            newSalesHistory.setInCartNumber7d(0);
            newSalesHistory.setInCardNumber(firstSalesHistories.getInCardNumber());
            newSalesHistory.setOnSalesDurationOffline(firstSalesHistories.getOnSalesDurationOffline());

            // 将新对象插入到列表的最前面
            salesHistories.add(0, newSalesHistory);

            firstSalesDate = salesHistories.get(0).getSalesDate();
        }

        return salesHistories;
    }

    @PostMapping("/getSalesHistorySKU")
    public Msg getSalesHistorySKU(@RequestParam("skcId") String skcId) {

        List<SalesHistorySKU> salesHistory = salesHistorySKUService.getSalesHistory(skcId);

        List<SalesHistorySKU> salesHistoryYesterday = salesHistorySKUService.getSalesHistorySKUYesterday(skcId);

        Map<String, Object> map = new HashMap<>();
        map.put("salesHistory3Day", salesHistory);
        map.put("salesHistory1Day", salesHistoryYesterday);

        return Msg.ok(map);
    }

    @PostMapping("/getSalesHistorySKUYesterday")
    public List<SalesHistorySKU> getSalesHistorySKUYesterday(@RequestParam("skcId") String skcId) {

        List<SalesHistorySKU> salesHistory = salesHistorySKUService.getSalesHistorySKUYesterday(skcId);

        return salesHistory;
    }

    @PostMapping("/getOneDaySaleHistoryShopProfit")
    public PageResult<MerSaleHistoryShopProfitDTO> getOneDaySaleHistoryShopProfit(@RequestParam("shopId") String shopId, @RequestParam("date") String date, @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) throws Exception {

        // 定义日期格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将传递的日期转换为 LocalDate
        LocalDate localDate = LocalDate.parse(date, dateFormatter);

        // 获取当天的起始时间和结束时间
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        // 将 LocalDateTime 格式化为字符串
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startOfDayStr = startOfDay.format(dateTimeFormatter);
        String endOfDayStr = endOfDay.format(dateTimeFormatter);

        List<Merchandise> allMerchandiseList = merService.list(new QueryWrapper<Merchandise>().eq("shopId", shopId).orderByDesc("createTime"));

        int total = allMerchandiseList.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);

        List<Merchandise> merchandiseList = allMerchandiseList.subList(startIndex, endIndex);

        List<MerSaleHistoryShopProfitDTO> merSaleHistoryShopProfitDTOS = new ArrayList<>();

        for (Merchandise merchandise : merchandiseList) {
            MerSaleHistoryShopProfitDTO merSaleAndCostDTO = MerToMerSaleHistoryShopProfitDTO.getInstance().doToDTO(merchandise);

            List<SalesHistorySKU> saleInfoList = salesHistorySKUService.list(
                    new QueryWrapper<SalesHistorySKU>()
                            .eq("skcId", merchandise.getSkcId())
                            .ge("createdTime", startOfDayStr)
                            .le("createdTime", endOfDayStr)
            );

            // 找子SKC
            SameKind findSon = sameKindService.getOne(new QueryWrapper<SameKind>().eq("skcId", merchandise.getSkcId()));

            String costSKC = merchandise.getSkcId();
            if (findSon != null) {
                costSKC = findSon.getMainSkcId();
            }

            Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", costSKC));

            merSaleAndCostDTO.setSaleInfoList(saleInfoList);

            merSaleAndCostDTO.setCost(cost);

            merSaleHistoryShopProfitDTOS.add(merSaleAndCostDTO);
        }


        // 封装到 PageResult 对象
        return new PageResult<>(merSaleHistoryShopProfitDTOS, total, totalPages, currentPage);

    }

}

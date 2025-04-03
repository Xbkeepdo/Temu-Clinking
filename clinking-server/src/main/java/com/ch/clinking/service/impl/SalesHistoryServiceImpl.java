package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.Dto.SalesCountDTO;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.SalesHistory;
import com.ch.clinking.mapper.CostMapper;
import com.ch.clinking.mapper.SalesHistoryMapper;
import com.ch.clinking.service.CostService;
import com.ch.clinking.service.SalesHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("salesHistoryService")
public class SalesHistoryServiceImpl extends ServiceImpl<SalesHistoryMapper, SalesHistory> implements SalesHistoryService {
    private static final int BATCH_SIZE = 500;
    @Resource
    SalesHistoryMapper salesHistoryMapper;

    @Resource CostService costService;
    @Override
    public double getSalesCountByProduct(String skcId, LocalDateTime startTime, LocalDateTime endTime) {
        Cost cost = costService.getOne(new QueryWrapper<Cost>().eq("skcId", skcId));
        double salesCount = salesHistoryMapper.selectCountBySkcId(skcId, startTime, endTime);
        if (cost == null) {
            return (double) salesCount;
        }

          return   (100 - cost.getQualityRefundRate()) * 0.01 * salesCount;
    }

//    @Override
//    public double getTotalSalesCount(List<String> skcIds, LocalDateTime startTime, LocalDateTime endTime) {
//        //  处理时间范围
//        LocalDateTime actualStart = startTime != null ? startTime : LocalDateTime.now().minusDays(30);
//        LocalDateTime actualEnd = endTime != null ? endTime : LocalDateTime.now();
//
////        double totalSalesCount = 0;
////
////        for(String skcId:skcIds){
////            totalSalesCount += getSalesCountByProduct(skcId, actualStart, actualEnd);
////        }
////
////        return totalSalesCount;
//
//        // 2. 批量查询成本数据（一次查询）
//        Map<String, Double> costRateMap = costService.list(
//                new QueryWrapper<Cost>().in("skcId", skcIds)
//        ).stream().collect(
//                Collectors.toMap(Cost::getSkcId, Cost::getQualityRefundRate)
//        );
//
//
//        System.out.println("costRateMap=======================" + costRateMap);
//
//        // 3. 批量查询销售数据（一次查询）
//        Map<String, Long> salesCountMap = salesHistoryMapper.batchSelectCountBySkcIds(
//                skcIds,
//                actualStart,
//                actualEnd
//        );
//
//        System.out.println("salesCountMap=======================" + salesCountMap);
//
//        // 4. 内存计算总和
//        return skcIds.stream()
//                .mapToDouble(skcId -> {
//                    long sales = salesCountMap.getOrDefault(skcId, 0L);
//                    double rate = costRateMap.getOrDefault(skcId, 0.0);
//                    return (100 - rate)* 0.01 * sales;
//                })
//                .sum();
//    }



    @Override
    public double getTotalSalesCount(List<String> skcIds, LocalDateTime startTime, LocalDateTime endTime) {
        // 1. 处理时间范围
        validateTimeRange(startTime, endTime);
        LocalDateTime actualStart = startTime != null ? startTime : LocalDateTime.now().minusDays(30);
        LocalDateTime actualEnd = endTime != null ? endTime : LocalDateTime.now();

        // 2. 分批查询成本数据（并行）
        Map<String, Double> costRateMap = splitAndQueryInParallel(
                skcIds,
                batch -> costService.listBySkcIds(batch)
        );

        // 3. 分批查询销售数据（并行）
        Map<String, Long> salesCountMap = splitAndQueryInParallel(
                skcIds,
                batch -> salesHistoryMapper.batchSelectCountBySkcIds(batch, actualStart, actualEnd)
        );

        // 4. 并行计算结果
        return skcIds.parallelStream()
                .mapToDouble(skcId -> calculateAdjustedSales(
                        salesCountMap.getOrDefault(skcId, 0L),
                        costRateMap.getOrDefault(skcId, 0.0)
                ))
                .sum();
    }

    // --- 核心工具方法 ---
    private <T, R> Map<String, R> splitAndQueryInParallel(List<String> skcIds, Function<List<String>, List<T>> queryFunction) {
        // 分批处理
        List<List<String>> batches = splitList(skcIds, BATCH_SIZE);

        // 并行查询并合并结果
        return batches.parallelStream()
                .map(queryFunction)
                .flatMap(List::stream)
                .collect(Collectors.toConcurrentMap(
                        this::extractSkcId, // 需要根据实际返回类型实现
                        this::extractValue, // 需要根据实际返回类型实现
                        (existing, replacement) -> existing
                ));
    }

    // --- 辅助方法 ---
    private List<List<String>> splitList(List<String> list, int batchSize) {
        return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
                .collect(Collectors.toList());
    }

    private String extractSkcId(Object obj) {
        if (obj instanceof Cost) {
            return ((Cost) obj).getSkcId();
        } else if (obj instanceof SalesCountDTO) {
            return ((SalesCountDTO) obj).getSkcId();
        }
        throw new IllegalArgumentException("Unsupported type");
    }

    private <R> R extractValue(Object obj) {
        if (obj instanceof Cost) {
            return (R) Double.valueOf(((Cost) obj).getQualityRefundRate());
        } else if (obj instanceof SalesCountDTO) {
            return (R) Long.valueOf(((SalesCountDTO) obj).getCount());
        }
        throw new IllegalArgumentException("Unsupported type");
    }

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("时间范围无效");
        }
    }

    private double calculateAdjustedSales(long sales, double refundRate) {
        return (100 - refundRate) * 0.01 * sales;
    }
}

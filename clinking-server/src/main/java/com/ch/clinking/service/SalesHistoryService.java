package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.SalesHistory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional  // 使用事务管理
public interface SalesHistoryService extends IService<SalesHistory> {

    double getSalesCountByProduct(String skcId, LocalDateTime startTime, LocalDateTime endTime);


    double getTotalSalesCount(List<String> skcIds, LocalDateTime startTime, LocalDateTime endTime);
}

package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.SalesHistory;
import com.ch.clinking.entity.SalesHistorySKU;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional  // 使用事务管理
public interface SalesHistorySKUService extends IService<SalesHistorySKU> {

    List<SalesHistorySKU> getSalesHistory(String skcId);

    List<SalesHistorySKU> getSalesHistorySKUYesterday(String skcId);

}

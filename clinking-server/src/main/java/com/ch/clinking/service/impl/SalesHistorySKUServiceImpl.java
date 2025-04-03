package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.SalesHistory;
import com.ch.clinking.entity.SalesHistorySKU;
import com.ch.clinking.mapper.OrderMapper;
import com.ch.clinking.mapper.SalesHistoryMapper;
import com.ch.clinking.mapper.SalesHistorySKUMapper;
import com.ch.clinking.service.SalesHistorySKUService;
import com.ch.clinking.service.SalesHistoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service("salesHistorySKUService")
public class SalesHistorySKUServiceImpl extends ServiceImpl<SalesHistorySKUMapper, SalesHistorySKU> implements SalesHistorySKUService {

    @Resource
    private SalesHistorySKUMapper salesHistorySKUMapper;

    @Override
    public List<SalesHistorySKU> getSalesHistory(String skcId) {
        return baseMapper.getSalesHistory(skcId);
    }

    @Override
    public List<SalesHistorySKU> getSalesHistorySKUYesterday(String skcId) {
        return baseMapper.getSalesHistorySKUYesterday(skcId);
    }

}

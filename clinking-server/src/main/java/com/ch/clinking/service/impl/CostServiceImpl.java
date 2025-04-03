package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.mapper.CostMapper;
import com.ch.clinking.service.CostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("costService")
public class CostServiceImpl extends ServiceImpl<CostMapper, Cost> implements CostService {

    @Resource
    private CostMapper costMapper;


    @Override
    public List<Cost> listBySkcIds(List<String> skcIds) {
        return costMapper.selectList(
                new QueryWrapper<Cost>()
                        .in("skcId", skcIds)
                        .select("skcId", "qualityRefundRate")
        );
    }
}

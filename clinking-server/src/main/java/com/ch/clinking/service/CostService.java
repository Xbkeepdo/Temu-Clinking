package com.ch.clinking.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import com.ch.clinking.mapper.CostMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional  // 使用事务管理
public interface CostService extends IService<Cost> {





    public List<Cost> listBySkcIds(List<String> skcIds);

}

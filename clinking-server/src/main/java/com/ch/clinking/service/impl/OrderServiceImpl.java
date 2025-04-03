package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import com.ch.clinking.mapper.OrderMapper;
import com.ch.clinking.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<OrderS2EDto> getOrderS2D(String shopId, String startTime, String endTime) {
        return baseMapper.getOrderS2D(shopId, startTime, endTime);
    }


}

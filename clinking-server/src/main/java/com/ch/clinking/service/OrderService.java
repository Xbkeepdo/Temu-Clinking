package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional  // 使用事务管理
public interface OrderService extends IService<Order> {

    List<OrderS2EDto> getOrderS2D(String shopId, String startTime, String endTime);

}

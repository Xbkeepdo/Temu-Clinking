package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import com.ch.clinking.entity.SameKind;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional  // 使用事务管理
public interface SameKindService extends IService<SameKind> {

    int existsBySkcId(String skcId);

}

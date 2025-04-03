package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.SalesHistoryShopBrief;
import org.springframework.transaction.annotation.Transactional;


@Transactional  // 使用事务管理
public interface SalesHistoryShopBriefService extends IService<SalesHistoryShopBrief> {


}

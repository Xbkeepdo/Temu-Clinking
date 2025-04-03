package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Merchandise;
import com.ch.clinking.entity.ProductionInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional  // 使用事务管理
public interface MerService extends IService<Merchandise> {


    Merchandise selectBySkcId(String skcId);

    int existsBySkcId(String skcId);

    List<String> selectAllSkcId(String shopId);

    String getProductInfoPDF(Merchandise merchandise, ProductionInfo productionInfo);

//    List<Merchandise> list(Map<String, Object> map);
//
//    Long getTotal(Map<String, Object> map);
//
//    void add(Merchandise merchandise);
//
//    void update(Merchandise merchandise);
//
//    Merchandise findById(Integer id);

}

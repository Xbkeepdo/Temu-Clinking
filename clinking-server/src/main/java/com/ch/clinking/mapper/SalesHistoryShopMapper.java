package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.SalesHistorySKU;
import com.ch.clinking.entity.SalesHistoryShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SalesHistoryShopMapper extends BaseMapper<SalesHistoryShop> {


}

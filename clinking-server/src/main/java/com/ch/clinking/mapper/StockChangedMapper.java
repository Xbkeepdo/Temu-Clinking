package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.StockChanged;
import com.ch.clinking.entity.UserShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockChangedMapper extends BaseMapper<StockChanged>{

}

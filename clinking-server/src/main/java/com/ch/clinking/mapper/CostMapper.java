package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CostMapper extends BaseMapper<Cost> {


}

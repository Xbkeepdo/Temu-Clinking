package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Dto.SalesCountDTO;
import com.ch.clinking.entity.Order;
import com.ch.clinking.entity.SalesHistory;

import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper

public interface SalesHistoryMapper extends BaseMapper<SalesHistory> {


    int selectCountBySkcId(@Param("skcId") String skcId, @Param("startTime") LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);

    //查询30天销售额



    // SalesHistoryMapper.java





    List<SalesCountDTO> batchSelectCountBySkcIds(
            @Param("skcIds") List<String> skcIds,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );



}

package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.SalesHistory;
import com.ch.clinking.entity.SalesHistorySKU;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SalesHistorySKUMapper extends BaseMapper<SalesHistorySKU> {

    @Select("SELECT " +
            "MAX(id) AS id, " +
            "skcId, " +
            "skuId, " +
            "className, " +
            "skuSizeNum, " +
            "MAX(salesDate) AS salesDate, " +
            "SUM(salesCount) AS salesCount, " +
            "MAX(createdTime) AS createdTime " +
            "FROM sales_history_sku " +
            "WHERE skcId = #{skcId} " +
            "AND salesDate >= CURDATE() - INTERVAL 3 DAY " +
            "GROUP BY skuId")
    List<SalesHistorySKU> getSalesHistory(@Param("skcId") String skcId);


    @Select("SELECT " +
            "MAX(id) AS id, " +
            "skcId, " +
            "skuId, " +
            "className, " +
            "skuSizeNum, " +
            "MAX(salesDate) AS salesDate, " +
            "SUM(salesCount) AS salesCount, " +
            "MAX(createdTime) AS createdTime " +
            "FROM sales_history_sku " +
            "WHERE skcId = #{skcId} " +
            "AND salesDate >= CURDATE() - INTERVAL 1 DAY " +
            "GROUP BY skuId")
    List<SalesHistorySKU> getSalesHistorySKUYesterday(@Param("skcId") String skcId);


}

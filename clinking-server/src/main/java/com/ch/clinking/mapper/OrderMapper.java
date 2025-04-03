package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT " +
            "shopId, " +
            "skcId, " +
            "state, " +
            "SUM(XXS) AS XXS, " +
            "SUM(XS) AS XS, " +
            "SUM(S) AS S, " +
            "SUM(M) AS M, " +
            "SUM(L) AS L, " +
            "SUM(XL) AS XL, " +
            "SUM(XXL) AS XXL, " +
            "SUM(Plus_0XL) AS Plus_0XL, " +
            "SUM(Plus_1XL) AS Plus_1XL, " +
            "SUM(Plus_2XL) AS Plus_2XL, " +
            "SUM(Plus_3XL) AS Plus_3XL, " +
            "SUM(Plus_4XL) AS Plus_4XL, " +
            "SUM(Plus_5XL) AS Plus_5XL, " +
            "SUM(Plus_6XL) AS Plus_6XL, " +
            "SUM(make_XXS) AS make_XXS, " +
            "SUM(make_XS) AS make_XS, " +
            "SUM(make_S) AS make_S, " +
            "SUM(make_M) AS make_M, " +
            "SUM(make_L) AS make_L, " +
            "SUM(make_XL) AS make_XL, " +
            "SUM(make_XXL) AS make_XXL, " +
            "SUM(make_Plus_0XL) AS make_Plus_0XL, " +
            "SUM(make_Plus_1XL) AS make_Plus_1XL, " +
            "SUM(make_Plus_2XL) AS make_Plus_2XL, " +
            "SUM(make_Plus_3XL) AS make_Plus_3XL, " +
            "SUM(make_Plus_4XL) AS make_Plus_4XL, " +
            "SUM(make_Plus_5XL) AS make_Plus_5XL, " +
            "SUM(make_Plus_6XL) AS make_Plus_6XL, " +
            "activate_XXS, " +
            "activate_XS, " +
            "activate_S, " +
            "activate_M, " +
            "activate_L, " +
            "activate_XL, " +
            "activate_XXL, " +
            "activate_Plus_0XL, " +
            "activate_Plus_1XL, " +
            "activate_Plus_2XL, " +
            "activate_Plus_3XL, " +
            "activate_Plus_4XL, " +
            "activate_Plus_5XL, " +
            "activate_Plus_6XL " +
            "FROM `order` " +
            "WHERE createdTime BETWEEN #{startTime} AND #{endTime} " +
            "AND shopId = #{shopId} " +
            "GROUP BY " +
            "shopId, " +
            "skcId, " +
            "state, " +
            "activate_XXS, " +
            "activate_XS, " +
            "activate_S, " +
            "activate_M, " +
            "activate_L, " +
            "activate_XL, " +
            "activate_XXL, " +
            "activate_Plus_0XL, " +
            "activate_Plus_1XL, " +
            "activate_Plus_2XL, " +
            "activate_Plus_3XL, " +
            "activate_Plus_4XL, " +
            "activate_Plus_5XL, " +
            "activate_Plus_6XL")
    List<OrderS2EDto> getOrderS2D(@Param("shopId") String shopId,
                                  @Param("startTime") String startTime,
                                  @Param("endTime") String endTime);

}

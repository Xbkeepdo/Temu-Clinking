package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    @Select("SELECT * FROM shop WHERE shopId = #{shopId}")
    Shop selectByShopId(String shopId);

    @Update("UPDATE shop SET bill_count = #{billCount} WHERE shop_id = #{shopId}")
    void updateBillCount(int billCount,String shopId);

    @Update("UPDATE shop SET shop_name = #{shopName} WHERE shop_id = #{shopId}")
    void updateShopName(String shopName,String shopId);

    @Select("SELECT shop.* FROM user_shop JOIN shop ON user_shop.shopId = shop.shopId WHERE user_shop.account = #{account}")
    List<Shop> selectByAccount(String account);

}


package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.User;
import com.ch.clinking.entity.UserShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserShopMapper extends BaseMapper<UserShop>{

    @Select("SELECT shop.* FROM user_shop JOIN shop ON user_shop.shopId = shop.shop_id WHERE user_shop.account = #{account}")
    List<Shop> selectByAccount(String account);

}

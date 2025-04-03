package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Designer;
import com.ch.clinking.entity.ProductDesigner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDesignerMapper extends BaseMapper<ProductDesigner> {
    // 插入新的绑定关系
    @Insert("INSERT INTO product_designer (skcId, name, shopId, shopName) VALUES (#{skcId}, #{name}, #{shopId}, #{shopName})")
    int insert(ProductDesigner productDesigner);

    // 查询是否已绑定
    @Select("SELECT * FROM product_designer WHERE skcId = #{skcId} AND shopId = #{shopId} AND name = #{name}")
    ProductDesigner findBySkcIdAndShopIdAndName(@Param("skcId") String skcId, @Param("shopId") String shopId, @Param("name") String name);

    // 删除绑定
    @Delete("DELETE FROM product_designer WHERE skcId = #{skcId} AND shopId = #{shopId} AND name = #{name}")
    int deleteBySkcIdAndShopIdAndName(@Param("skcId") String skcId, @Param("shopId") String shopId, @Param("name") String name);

    @Select("select skcId from product_designer where shopId = #{shopId} and name = #{nickName}")
    List<String> searchAllByDesigner(@Param("shopId") String  shopId, @Param("nickName") String  nickName);

    @Select("select name from product_designer where shopId = #{shopId} and skcId = #{skcId}")
    List<String> selectAllDesignersByShopIdAndSkcId( @Param("skcId") String  skcId,@Param("shopId") String  shopId);

}




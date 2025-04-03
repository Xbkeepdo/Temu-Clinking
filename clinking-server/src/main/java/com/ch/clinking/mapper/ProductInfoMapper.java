package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.ProductInfo;
import com.ch.clinking.entity.ProductionInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    @Select("SELECT * FROM productInfo WHERE skcId = #{skcId}")
    ProductInfo selectBySkcId(@Param("skcId") String skcId);


    ProductInfo updateBySkcId(@Param("skcId") String skcId,@Param("detailImg")String detailImg);
}

package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ch.clinking.entity.Product;
import com.ch.clinking.entity.ProductDesigner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {


        @Transactional
        void updateStateBySkcIdInt(@Param("skcId") String skcId,@Param("state") String state );

        void searchAllByShopId(@Param("shopId") String shopId);



}




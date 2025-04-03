package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.Merchandise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MerMapper extends BaseMapper<Merchandise> {

    List<Merchandise> list(Map<String, Object> map);

    Long getTotal(Map<String, Object> map);


    void add(Merchandise merchandise);

    void update(Merchandise merchandise);

    Merchandise findById(Integer id);

    @Select("SELECT * FROM merchandise WHERE skc_id = #{skcId}")
    Merchandise selectBySkcId(@Param("skcId") String skcId);

    @Select("SELECT skcId FROM merchandise WHERE shopId = #{shopId}")
    List<String> selectAllSkcId(@Param("shopId") String shopId);

    @Select("SELECT COUNT(*) FROM merchandise WHERE skcId = #{skcId}")
    int existsBySkcId(@Param("skcId") String skcId);


}

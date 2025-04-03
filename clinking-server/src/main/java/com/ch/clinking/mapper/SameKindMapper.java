package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.SameKind;
import com.ch.clinking.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SameKindMapper extends BaseMapper<SameKind> {

    @Select("SELECT COUNT(*) FROM same_kind WHERE skcId = #{skcId}")
    int existsBySkcId(@Param("skcId") String skcId);


}

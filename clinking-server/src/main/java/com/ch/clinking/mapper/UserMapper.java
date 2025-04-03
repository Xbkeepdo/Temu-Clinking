package com.ch.clinking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.clinking.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE account = #{account}")
    User selectByAccount(String account);

    @Select("SELECT * FROM user WHERE shop_id = #{shopId}")
    List<User> selectMemberByShopId(String shopId);

    @Update("UPDATE user SET save_money = #{saveMoney} WHERE account = #{account}")
    void updateSaveMoney(Double saveMoney,String account);

    @Update("UPDATE user SET total_money = #{totalMoney} WHERE account = #{account}")
    void updateTotalMoney(Double totalMoney,String account);

    @Update("UPDATE user SET head_image = #{headImage} WHERE account = #{account}")
    void updateHeadImage(String headImage,String account);

    @Update("UPDATE user SET nick_name = #{nickName},gender = #{gender},update_time = #{updateTime},update_by = #{updateBy} WHERE account = #{account}")
    void updateUserProfile(String nickName, String gender, String updateTime, String updateBy, String account);

    @Select("SELECT id, account, password, nick_name AS nickName, shop_id AS shopId, status, remark FROM user WHERE account = #{account} and password = #{password}")
    User selectByAccountAndPassword(@Param("account") String account, @Param("password") String password);


}

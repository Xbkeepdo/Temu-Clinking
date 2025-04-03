package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.User;

import java.util.List;


public interface UserService extends IService<User> {

    User selectByAccount(String account);

    List<User> selectMemberByShopId(String shopId);

    void updateSaveMoney(Double saveMoney,String account);

    void updateTotalMoney(Double totalMoney,String account);

    void updateHeadImage(String headImage,String account);

    void updateUserProfile(String nickName, String gender, String updateTime, String updateBy, String account);

//    void loginMonitor(String account, String shopId);

    User checkLogin(String account, String password);

}

package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.User;
import com.ch.clinking.mapper.UserMapper;
import com.ch.clinking.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByAccount(String account) {
        return baseMapper.selectByAccount(account);
    }

    @Override
    public List<User> selectMemberByShopId(String shopId) {
        return baseMapper.selectMemberByShopId(shopId);
    }

    @Override
    public void updateSaveMoney(Double saveMoney, String account) {
        baseMapper.updateSaveMoney(saveMoney, account);
    }

    @Override
    public void updateTotalMoney(Double totalMoney, String account) {
        baseMapper.updateTotalMoney(totalMoney, account);
    }

    @Override
    public void updateHeadImage(String headImage, String account) {
        baseMapper.updateHeadImage(headImage, account);
    }

    @Override
    public void updateUserProfile(String nickName, String gender, String updateTime, String updateBy, String account) {
        baseMapper.updateUserProfile(nickName, gender, updateTime, updateBy, account);
    }

    @Override
    public User checkLogin(String account, String password) {
        return userMapper.selectByAccountAndPassword(account,password);

    }

}

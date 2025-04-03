package com.ch.clinking.entity.Dto;

import com.ch.clinking.entity.User;

import java.util.List;

public class AddMemberRequest {
    private User user;
    private List<String> shopIdList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getShopIdList() {
        return shopIdList;
    }

    public void setShopIdList(List<String> shopIdList) {
        this.shopIdList = shopIdList;
    }

    @Override
    public String toString() {
        return "AddMemberRequest{" +
                "user=" + user +
                ", shopIdList=" + shopIdList +
                '}';
    }
}
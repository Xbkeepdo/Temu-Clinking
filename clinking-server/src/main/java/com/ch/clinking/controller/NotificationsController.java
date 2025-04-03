package com.ch.clinking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ch.clinking.entity.Merchandise;
import com.ch.clinking.entity.Msg;
import com.ch.clinking.entity.Notifications;
import com.ch.clinking.service.NotificationsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Resource
    private NotificationsService notificationsService;

    @PostMapping("/select30SalesPointByUserId")
    public List<Notifications> selectByUserId(@RequestParam("shopId")String shopId, @RequestParam("userId")int userId) {
        List<Notifications> notifications = notificationsService.list(new QueryWrapper<Notifications>().eq("userId", userId).eq("shopId", shopId).eq("type", "30SalesPoint").eq("isConfirmed", 0).orderByDesc("createdTime"));
        return notifications;
    }

    @PostMapping("/select30SalesPointByUserIdAllState")
    public List<Notifications> selectByUserIdAllState(@RequestParam("shopId")String shopId, @RequestParam("userId")int userId) {
        List<Notifications> notifications = notificationsService.list(new QueryWrapper<Notifications>().eq("userId", userId).eq("shopId", shopId).eq("type", "30SalesPoint").orderByDesc("createdTime"));
        return notifications;
    }

    @PostMapping("/updateNotifications")
    public Msg updateNotifications(@RequestBody List<Notifications> notifications) {
        for (Notifications n : notifications) {
            notificationsService.saveOrUpdate(n);
        }
        return Msg.ok();
    }

    @PostMapping("/deleteNotification")
    public Msg deleteNotification(@RequestParam("id")int id) {
        notificationsService.remove(Wrappers.<Notifications>query().lambda().eq(Notifications::getId, id));
        return Msg.ok();
    }

}

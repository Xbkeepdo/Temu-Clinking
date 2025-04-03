package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Notifications;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.mapper.NotificationsMapper;
import com.ch.clinking.mapper.ShopMapper;
import com.ch.clinking.service.NotificationsService;
import com.ch.clinking.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("notificationsService")
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications> implements NotificationsService {


}

package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Shop;
import com.ch.clinking.entity.UserSet;
import com.ch.clinking.mapper.ShopMapper;
import com.ch.clinking.mapper.UserSetMapper;
import com.ch.clinking.service.ShopService;
import com.ch.clinking.service.UserSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userSetService")
public class UserSetServiceImpl extends ServiceImpl<UserSetMapper, UserSet> implements UserSetService {

}

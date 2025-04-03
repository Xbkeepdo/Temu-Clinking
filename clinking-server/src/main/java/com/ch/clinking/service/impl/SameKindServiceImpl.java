package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.entity.Order;
import com.ch.clinking.entity.SameKind;
import com.ch.clinking.mapper.MerMapper;
import com.ch.clinking.mapper.OrderMapper;
import com.ch.clinking.mapper.SameKindMapper;
import com.ch.clinking.service.OrderService;
import com.ch.clinking.service.SameKindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sameKindServer")
public class SameKindServiceImpl extends ServiceImpl<SameKindMapper, SameKind> implements SameKindService {

    @Resource
    private SameKindMapper sameKindMapper;

    @Override
    public int existsBySkcId(String skcId) {
        return sameKindMapper.existsBySkcId(skcId);
    }
}

package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Cost;
import com.ch.clinking.entity.Designer;
import com.ch.clinking.mapper.CostMapper;
import com.ch.clinking.mapper.DesignerMapper;
import com.ch.clinking.service.CostService;
import com.ch.clinking.service.DesignerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("designerService")
public class DesignerServiceImpl extends ServiceImpl<DesignerMapper, Designer> implements DesignerService {


}

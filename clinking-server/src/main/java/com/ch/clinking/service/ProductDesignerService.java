package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.Designer;
import com.ch.clinking.entity.ProductDesigner;
import com.ch.clinking.mapper.ProductDesignerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductDesignerService extends IService<ProductDesigner> {



    public boolean bindProduct(ProductDesigner productDesigner);

    public boolean unbindProduct(ProductDesigner productDesigner);

    public boolean isProductBound(String skcId, String shopId, String name);



    public List<String> getDesignerNameListBySkcId(String skcId);

}

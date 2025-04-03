package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Designer;
import com.ch.clinking.entity.ProductDesigner;
import com.ch.clinking.mapper.DesignerMapper;
import com.ch.clinking.mapper.ProductDesignerMapper;
import com.ch.clinking.service.DesignerService;
import com.ch.clinking.service.ProductDesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("productDesignerService")
public class ProductDesignerServiceImpl extends ServiceImpl<ProductDesignerMapper, ProductDesigner> implements ProductDesignerService {

    @Autowired
    private ProductDesignerMapper productDesignerMapper;

    public boolean bindProduct(ProductDesigner productDesigner) {
        // 先检查是否已绑定
        ProductDesigner existing = productDesignerMapper.findBySkcIdAndShopIdAndName(
                productDesigner.getSkcId(), productDesigner.getShopId(), productDesigner.getName());
        if (existing == null) {
            return productDesignerMapper.insert(productDesigner) > 0;
        }
        return false; // 已绑定，避免重复绑定
    }

    public boolean unbindProduct(ProductDesigner productDesigner) {
        return productDesignerMapper.deleteBySkcIdAndShopIdAndName(
                productDesigner.getSkcId(), productDesigner.getShopId(), productDesigner.getName()) > 0;
    }

    public boolean isProductBound(String skcId, String shopId, String name) {
        return productDesignerMapper.findBySkcIdAndShopIdAndName(skcId, shopId, name) != null;
    }

    @Override
    public List<String> getDesignerNameListBySkcId(String skcId) {

        List<String> designerList =  new ArrayList<>();

        //获取设计师名字列表
        List<ProductDesigner> designers = productDesignerMapper.selectList(new QueryWrapper<ProductDesigner>()
                .eq("skcId", skcId)
                .select("DISTINCT name"));

        for(ProductDesigner designer : designers){
            designerList.add(designer.getName());
        }


        // 需要移除的特定元素
        Set<String> elementsToRemove = new HashSet<>(Arrays.asList("林招财", "沫瀛唸", "Top"));

// 使用 removeIf 移除
        designerList.removeIf(elementsToRemove::contains);

        return designerList;


    }
}

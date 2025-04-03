package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.ProductInfo;
import com.ch.clinking.entity.ProductionInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ProductInfoService extends IService<ProductInfo> {

    public ProductInfo saveProductInfo(ProductInfo info);


    public ProductInfo getProductInfo(String skcId);

    public ProductInfo uploadImage(MultipartFile file, String skcId) throws IOException;


    public void removeImageFromRecord(String skcId, String filename);
    public String storeFile(MultipartFile file, String skcId) throws IOException;
}

package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.ProductInfo;
import com.ch.clinking.entity.ProductionInfo;
import com.ch.clinking.mapper.ProductInfoMapper;
import com.ch.clinking.mapper.ProductionInfoMapper;
import com.ch.clinking.service.ProductInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {


    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo saveProductInfo(ProductInfo info) {

        if (productInfoMapper.exists(new QueryWrapper<ProductInfo>().eq("skcId", info.getSkcId()))) {
            productInfoMapper.update(info, new UpdateWrapper<ProductInfo>().eq("skcId", info.getSkcId()));

            System.out.println("更新成功===========================================================");
        } else {
            productInfoMapper.insert(info);
        }

        return info;
    }

    @Override
    public ProductInfo getProductInfo(String skcId) {
        return productInfoMapper.selectBySkcId(skcId);
    }


    @Transactional
    public ProductInfo uploadImage(MultipartFile file, String skcId) throws IOException {
        ProductInfo productInfo = productInfoMapper.selectBySkcId(skcId);

        // 初始化图片列表
   //     List<String> imgList = new ArrayList<>();
//        if (StringUtils.isNotBlank(productInfo.getDetailImg())) {
//            imgList = Arrays.asList(productInfo.getDetailImg().split(","));
//        }

        // 存储新文件
        String fileName = storeFile(file, skcId); // 提取存储方法
        //imgList.add(fileName);

        // 3. 更新图片列表
        String newDetailImg = StringUtils.isBlank(productInfo.getDetailImg()) ?
                fileName :
                productInfo.getDetailImg() + "," + fileName;

        // 更新字段（使用逗号分隔）
       // productInfo.setDetailImg(String.join(",", imgList));

        productInfo.setDetailImg(newDetailImg);
        System.out.println("======================地址转换完成======================");
       // productInfoMapper.updateBySkcId(productInfo.getSkcId(), productInfo.getDetailImg());

        return productInfo;
    }
//    @Override
//    public ProductInfo uploadImage(MultipartFile file, String skcId) {
//        ProductInfo productInfo = productInfoMapper.selectBySkcId(skcId);
//
//        String fileName = file.getOriginalFilename();
//
//        // 获取项目根路径
//        String projectRootPath = System.getProperty("user.dir");
//
//        // 构建文件存储路径，确保路径分隔符适应不同操作系统
//        String uploadDir = projectRootPath + File.separator + "ProductInfoImage" + File.separator + skcId;
//        String fileUrl = "ProductInfoImage" + File.separator + skcId + File.separator + fileName;  // 相对路径，存储在数据库中的路径
//
//        System.out.println("上传目录: " + uploadDir);
//        System.out.println("文件URL: " + fileUrl);
//
//        // 确保文件夹存在
//        File directory = new File(uploadDir);
//        if (!directory.exists()) {
//            boolean dirsCreated = directory.mkdirs();  // 创建文件夹
//            if (!dirsCreated) {
//                throw new RuntimeException("文件夹创建失败: " + uploadDir);
//            }
//        }
//
//        // 存储文件
//        File dest = new File(uploadDir + File.separator + fileName);
//        try {
//            file.transferTo(dest);  // 将文件上传到指定位置
//        } catch (IOException e) {
//            throw new RuntimeException("文件上传失败", e);
//        }
//        productInfo.setDetailImg(fileUrl);
//
//        return productInfo;
//    }


    // 独立文件存储方法
    public String storeFile(MultipartFile file, String skcId) throws IOException {
        String fileName = file.getOriginalFilename();

        Path uploadDir = Paths.get(
                System.getProperty("user.dir"),
                "ProductInfoImage",
                skcId
        );

        Files.createDirectories(uploadDir);
        Path targetPath = uploadDir.resolve(fileName);
        file.transferTo(targetPath);

        String fileUrl = skcId + "/" + fileName;  // 相对路径，存储在数据库中的路径

//        String fileUrl = "ProductInfoImage" + File.separator + skcId + File.separator + fileName;  // 相对路径，存储在数据库中的路径

        return fileUrl; // 返回相对路径
    }

    // ProductInfoService.java 添加方法
    public void removeImageFromRecord(String skcId, String fileName) {
        ProductInfo productInfo = productInfoMapper.selectBySkcId(skcId);
        if (productInfo != null && StringUtils.isNotBlank(productInfo.getDetailImg())) {
            List<String> images = new ArrayList<>(Arrays.asList(productInfo.getDetailImg().split(",")));
            images.removeIf(img -> img.endsWith(fileName));
            productInfo.setDetailImg(String.join(",", images));
            productInfoMapper.update(productInfo, new UpdateWrapper<ProductInfo>().eq("skcId", skcId));
        }
    }
}

package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ch.clinking.entity.ProductInfo;
import com.ch.clinking.entity.ProductionInfo;
import com.ch.clinking.entity.Result;
import com.ch.clinking.mapper.ProductInfoMapper;
import com.ch.clinking.service.ProductInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
//import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productInfo")
@RequiredArgsConstructor
public class ProductInfoController {

    @javax.annotation.Resource
    private ProductInfoService productInfoService;

    @javax.annotation.Resource
    private ProductInfoMapper productInfoMapper;

    @PostMapping("/save")
    public Result saveProductionInfo(@RequestBody ProductInfo info) {
        productInfoService.saveProductInfo(info);
        return Result.success(info);
    }

    @GetMapping("/{skcId}")
    public ResponseEntity<ProductInfo> getProductionInfo(
            @PathVariable String skcId) {
        return ResponseEntity.ok(productInfoService.getProductInfo(skcId));
    }

    @GetMapping("/images/{skcId}")
    public Result getImageList(@PathVariable String skcId) {
        ProductInfo productInfo = productInfoService.getProductInfo(skcId);
        if (productInfo == null || StringUtils.isBlank(productInfo.getDetailImg())) {
            return Result.success(Collections.emptyList());
        }

        List<String> imgUrls = Arrays.stream(productInfo.getDetailImg().split(","))
                .map(path -> "/api/productInfo/images/" + path)
                .collect(Collectors.toList());

        return Result.success(imgUrls);
    }
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("skcId") String skcId) {
        try {

            ProductInfo productInfo = productInfoService.uploadImage(file, skcId);
            productInfoService.saveOrUpdate(productInfo);
            return Result.success(productInfo);
        } catch (Exception e) {
            return Result.error(0, "文件上传失败");
        }
    }


    // 图片查看接口
    @GetMapping("/images/{skcId}/{filename:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String skcId,
            @PathVariable String filename) {

        // 使用统一文件分隔符
        String uploadDir = System.getProperty("user.dir")
                + File.separator + "ProductInfoImage"
                + File.separator + skcId;

        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 更严谨的存在性检查
            if (resource.exists() && resource.isReadable()) {
                // 动态判断文件类型
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 图片删除接口
    @DeleteMapping("/deleteImage")
    public Result deleteImage(
            @RequestParam String url,
            @RequestParam String skcId) {

        try {
            // 解析文件名
            String fileName = url.substring(url.lastIndexOf("/") + 1);

            // 构建文件路径
            Path filePath = Paths.get(
                    System.getProperty("user.dir"),
                    "ProductInfoImage",
                    skcId,
                    fileName
            ).normalize();

            // 删除文件
            Files.deleteIfExists(filePath);

            // 更新数据库记录
            productInfoService.removeImageFromRecord(skcId, fileName);

            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(500, "删除失败: " + e.getMessage());
        }
    }
}
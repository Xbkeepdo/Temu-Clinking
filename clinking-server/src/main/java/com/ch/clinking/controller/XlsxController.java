package com.ch.clinking.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")  // 你可以根据需要更改这个路径
public class XlsxController {

    private static final String XLSX_DIRECTORY = "/root/Clinking/Xlsx/ProductInfo/";
//    private static final String XLSX_DIRECTORY = "D:\\";

    @GetMapping("/{fileName}") // 使用正则表达式支持包含点号的文件名
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            // 构建文件路径
            Path filePath = Paths.get(XLSX_DIRECTORY, fileName);

            // 读取文件字节流
            byte[] fileBytes = Files.readAllBytes(filePath);

            // 创建字节流资源
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            // 返回文件资源
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileBytes.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // 适用于文件下载
                    .body(resource);
        } catch (IOException e) {
            // 如果发生错误，返回404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

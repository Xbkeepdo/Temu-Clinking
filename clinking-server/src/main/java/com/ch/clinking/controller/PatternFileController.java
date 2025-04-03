package com.ch.clinking.controller;


import com.ch.clinking.entity.PatternFile;
import com.ch.clinking.entity.Result;
import com.ch.clinking.mapper.PatternFileMapper;
import com.ch.clinking.service.PatternFileService;
import com.ch.clinking.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patternFile")
public class PatternFileController {



    @Resource
    private PatternFileService patternFileService;
    @Resource
    private PatternFileMapper patternFileMapper;

    // 获取历史纸样文件
    @GetMapping("/getPatternFiles")
    public List<PatternFile> getPatternFiles(@RequestParam String skcId) {
        return patternFileService.getPatternFiles(skcId);
    }

    // 上传纸样文件
    @PostMapping("/uploadPattern")
    public Result uploadPatternFile(@RequestParam("file") MultipartFile file, @RequestParam String skcId) {
        try {
            PatternFile patternFile = patternFileService.uploadPatternFile(file, skcId);
            return Result.success(patternFile);
        } catch (Exception e) {
            return Result.error(0,"上传失败");
        }
    }

    // 删除纸样文件
    @PostMapping("/deletePatternFile")
    public Result deletePatternFile(@RequestBody Map<String, Object> requestBody) {
        String skcId = (String) requestBody.get("skcId");
        String fileName = (String) requestBody.get("fileName");

        if (patternFileService.deletePatternFile(skcId, fileName)) {
            return Result.success("删除成功");
        }
        return Result.error(0, "删除失败");
    }


    // 文件下载接口
    @GetMapping("/downloadPatternFile")
    public void downloadPatternFile(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        // 根据文件ID从数据库查询
        PatternFile patternFile = patternFileMapper.selectById(fileId);
        if (patternFile == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 如果文件未找到
            return;
        }

        // 获取当前工程的根目录
        String baseDir = System.getProperty("user.dir");

        // 获取文件的相对路径
        String filePath = patternFile.getFileUrl();

        // 拼接出文件的完整路径
        File file = new File(Paths.get(baseDir, filePath).toString());

        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 如果文件不存在
            return;
        }

        // 设置响应头信息
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        response.setContentType("application/octet-stream"); // 设置文件类型为二进制流

        // 设置文件的大小（可选）
        response.setContentLengthLong(file.length());

        try (OutputStream outStream = response.getOutputStream()) {
            // 将文件内容写入输出流
            Files.copy(file.toPath(), outStream);
            outStream.flush(); // 确保文件完全写入响应
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

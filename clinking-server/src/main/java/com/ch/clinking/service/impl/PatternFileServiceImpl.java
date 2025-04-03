package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.PatternFile;
import com.ch.clinking.mapper.PatternFileMapper;
import com.ch.clinking.service.PatternFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service("patternFileService")
public class PatternFileServiceImpl extends ServiceImpl<PatternFileMapper, PatternFile> implements PatternFileService {
    @Resource
    private PatternFileMapper patternFileMapper;

    // 获取纸样文件
    public List<PatternFile> getPatternFiles(String skcId) {
      //  return patternFileMapper.findBySkcId(skcId);

        return patternFileMapper.selectList(new QueryWrapper<PatternFile>().eq("skcId",skcId));
    }

    // 上传文件

    public PatternFile uploadPatternFile(MultipartFile file, String skcId) {
        String fileName = file.getOriginalFilename();

        // 获取项目根路径
        String projectRootPath = System.getProperty("user.dir");

        // 构建文件存储路径，确保路径分隔符适应不同操作系统
        String uploadDir = projectRootPath + File.separator + "patternFiles" + File.separator + skcId;
        String fileUrl = "patternFiles" + File.separator + skcId + File.separator + fileName;  // 相对路径，存储在数据库中的路径

        System.out.println("上传目录: " + uploadDir);
        System.out.println("文件URL: " + fileUrl);

        // 确保文件夹存在
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();  // 创建文件夹
            if (!dirsCreated) {
                throw new RuntimeException("文件夹创建失败: " + uploadDir);
            }
        }

        // 存储文件
        File dest = new File(uploadDir + File.separator + fileName);
        try {
            file.transferTo(dest);  // 将文件上传到指定位置
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }

        // 创建 PatternFile 对象并保存文件信息
        PatternFile patternFile = new PatternFile();
        patternFile.setSkcId(skcId);
        patternFile.setFileName(fileName);
        patternFile.setFileUrl(fileUrl);  // 保存相对路径
        patternFile.setUploadTime(System.currentTimeMillis());

        // 假设你有 patternFileMapper 来执行数据库插入
        patternFileMapper.insert(patternFile);  // 保存到数据库

        return patternFile;
    }

//    public PatternFile uploadPatternFile(MultipartFile file, String skcId) {
//        String fileName = file.getOriginalFilename();
//        String fileUrl = "/patternFiles/" + skcId + "/" + fileName;
//        long uploadTime = System.currentTimeMillis();
//
//        // 存储文件
//        File dest = new File(fileUrl);
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // 保存到数据库
//        PatternFile patternFile = new PatternFile();
//        patternFile.setSkcId(skcId);
//        patternFile.setFileName(fileName);
//        patternFile.setFileUrl(fileUrl);
//        patternFile.setUploadTime(uploadTime);
//
//        patternFileMapper.insert(patternFile);
//
//        return patternFile;
//    }

    // 删除文件
    public boolean deletePatternFile(String skcId, String fileName) {
        // 根据文件 ID 从数据库获取文件记录
        PatternFile file = patternFileMapper.selectOne(new QueryWrapper<PatternFile>().eq("skcId", skcId).eq("fileName", fileName));
        if (file != null) {
            // 获取文件的相对路径
            String filePath = file.getFileUrl();

            // 获取当前工程的绝对路径
            String baseDir = System.getProperty("user.dir");  // 获取当前工程的根目录
            // 拼接出文件的完整路径
            File fileToDelete = new File(Paths.get(baseDir, filePath).toString());

            if (!fileToDelete.canWrite()) {
                fileToDelete.setWritable(true);
                // 处理权限不足的情况
                System.out.println("权限不足，无法删除文件");
            }

            if (!fileToDelete.exists()) {
                System.err.println("文件不存在：" + fileToDelete.getAbsolutePath());
            }
            // 确认文件存在并删除
            if (fileToDelete.exists() && fileToDelete.delete()) {
                // 文件删除成功，删除数据库中的记录
                patternFileMapper.delete(new QueryWrapper<PatternFile>().eq("skcId", skcId).eq("fileName", fileName));
                return true;
            } else {
                // 文件删除失败，记录日志

                System.err.println("删除文件失败，路径：" + fileToDelete.getAbsolutePath());
            }
        }
        return false;
    }
}

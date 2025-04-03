package com.ch.clinking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.clinking.entity.PatternFile;
import com.ch.clinking.mapper.PatternFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public interface PatternFileService extends IService<PatternFile> {

    // 获取纸样文件
    public List<PatternFile> getPatternFiles(String skcId);



    // 上传文件
    public PatternFile uploadPatternFile(MultipartFile file, String skcId);


    // 删除文件
    public boolean deletePatternFile(String skcId,String fileName);
}

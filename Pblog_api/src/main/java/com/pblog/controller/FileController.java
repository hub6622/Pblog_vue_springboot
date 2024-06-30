package com.pblog.controller;

import com.pblog.pojo.Result;
import com.pblog.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileController {
    private static final String FILE_PATH = "G:\\filefafa\\";
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {

        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());

        return new Result(200,"上传成功",url);
    }
}

package com.javaweb.controller;


import com.javaweb.entity.Result;
import com.javaweb.utils.AliOssUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping(value = "/upload")
    public Result<String> fileUpload(MultipartFile multipartFile ,HttpServletRequest request) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        //最终文件名
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+originalFilename.substring(originalFilename.lastIndexOf("."));
        //调用工具类
        String upload = AliOssUtil.upload(fileName, multipartFile.getInputStream());
//        String realPath = request.getServletContext().getRealPath("/file/")+fileName;
//        String fileName=request.getParameter("filename")+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
//        System.out.println(realPath);
//        File file = new File(realPath);
//        multipartFile.transferTo(file);
        return Result.success(upload);
    }
}

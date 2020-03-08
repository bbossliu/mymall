package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.service.fileupload.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dalaoban
 * @create 2020-03-07-18:49
 */
@CrossOrigin
@Controller
public class FileUploadController {

    @Reference
    FileUploadService fileUploadService;

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String name = file.getName();
            String size = String.valueOf(file.getSize());
            String originalFilename = file.getOriginalFilename();
            String imgUrl = fileUploadService.fileUpload(bytes, name, size, originalFilename);
            return imgUrl;
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

}

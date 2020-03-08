package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.ReturnT;
import com.atguigu.gmall.service.fileupload.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dalaoban
 * @create 2020-02-20-17:51
 */
@Controller
//@RequestMapping("manage")
public class ManageController {
//
//    @Reference
//    FileUploadService fileUploadService;
//
//    @RequestMapping("hello")
//    @LoginRequired
//    public String hello(){
//        return "hello";
//    }
//
//    @PostMapping("/fileUpload")
//    @ResponseBody
//    public ReturnT fileUpload(@RequestParam("file") MultipartFile file){
//        try {
//            byte[] bytes = file.getBytes();
//            String name = file.getName();
//            String size = String.valueOf(file.getSize());
//            String originalFilename = file.getOriginalFilename();
//            String filePath = fileUploadService.fileUpload(bytes, name, size, originalFilename);
//            return new ReturnT(ReturnT.SUCCESS_CODE,filePath);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        return new ReturnT(ReturnT.FAIL_CODE,"上传失败");
//    }
}

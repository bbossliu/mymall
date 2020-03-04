package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.service.fileupload.FileUploadService;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * @author dalaoban
 * @create 2020-03-01-12:01
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${fastdfs.fileServer.url}")
    String fileUrl;

    /**
     *
     * @param bytes 文件字节数组
     * @param name  文件名
     * @param size  文件打下
     * @param originalFilename 文件原始名字
     * @return
     * @throws IOException
     * @throws MyException
     */
    public String fileUpload( byte[] bytes,String name,String size,String originalFilename) throws Exception {

        String imgUrl=fileUrl;
        if(bytes!=null){
            System.out.println("multipartFile = " + name+"|"+size);

            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String extName = StringUtils.substringAfterLast(originalFilename, ".");

            String[] upload_file = storageClient.upload_file(bytes, extName, null);
            imgUrl=fileUrl ;
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl+="/"+path;
            }

        }

        return imgUrl;
    }

}

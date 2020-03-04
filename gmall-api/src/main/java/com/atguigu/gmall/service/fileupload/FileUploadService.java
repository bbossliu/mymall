package com.atguigu.gmall.service.fileupload;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dalaoban
 * @create 2020-03-01-12:02
 */
public interface FileUploadService {


    String fileUpload( byte[] bytes,String name,String size,String originalFilename) throws Exception;

}

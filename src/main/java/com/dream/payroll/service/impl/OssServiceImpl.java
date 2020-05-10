package com.dream.payroll.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dream.payroll.service.OssService;
import com.dream.payroll.util.PropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = PropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = PropertiesUtil.KEY_ID;
        String accessKeySecret = PropertiesUtil.KEY_SECRET;
        String bucketName=PropertiesUtil.BUCKET_NAME;
        String filename=file.getOriginalFilename();
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            filename=uuid+filename;
            //按日期进行分类
            String datePath=new DateTime().toString("yyyy/MM/dd");
            filename=datePath+"/"+filename;
            //第二个参数是上传的文件路径和名称
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            String url="https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}



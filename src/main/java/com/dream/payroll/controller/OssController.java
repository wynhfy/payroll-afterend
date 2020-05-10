package com.dream.payroll.controller;

import com.dream.payroll.result.Result;
import com.dream.payroll.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/payroll/oss/avatar")
@CrossOrigin
public class OssController {

     @Autowired
     private OssService ossService;

     @PostMapping
     public Result uploadAvatar(MultipartFile file){
         String url=ossService.uploadFile(file);
         return Result.ok().data("url",url);
     }

}

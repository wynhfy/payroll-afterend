package com.dream.payroll.controller;

import com.dream.payroll.result.Result;
import com.dream.payroll.service.MsmService;
import com.dream.payroll.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payroll/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public Result sendMessage(@PathVariable("phone") String phone){
        String code=redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }
        //获取四位随机数用于发短信
        code= RandomUtil.getFourBitRandom();
        Map<String,String> params=new HashMap<>();
        params.put("code",code);
        boolean issend=msmService.send(params,phone);
        if(issend){
            //把验证码放入redis中，并设置有效期
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.error().message("发送短信失败");
        }
    }




}

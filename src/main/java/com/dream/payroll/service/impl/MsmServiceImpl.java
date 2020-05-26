package com.dream.payroll.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dream.payroll.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    @Override
    public boolean send(Map<String, String> params, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "", "");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();


        //固定格式
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        request.putQueryParameter("PhoneNumbers", phone);     //电话号码
        request.putQueryParameter("SignName", "payroll的工资管理");
        request.putQueryParameter("TemplateCode", "SMS_189831526");  //aliyun模版code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params)); //这里规定返回json格式
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

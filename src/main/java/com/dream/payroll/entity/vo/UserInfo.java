package com.dream.payroll.entity.vo;

import lombok.Data;

/**
 * 登录后返回给前端的用户信息
 */
@Data
public class UserInfo {

    private String id;
    private String employeeId;
    private String name;
    private String mobile;

}

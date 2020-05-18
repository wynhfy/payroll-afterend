package com.dream.payroll.entity.vo;

import lombok.Data;

@Data
public class AttendanceVo {

    private String employeeId;
    private String status;
    private String begin;
    private String end;

}

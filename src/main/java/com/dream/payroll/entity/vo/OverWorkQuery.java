package com.dream.payroll.entity.vo;

import lombok.Data;

@Data
public class OverWorkQuery {

    private String employeeId;
    private String status;
    private String typeId;
    private String begin;
    private String end;

}

package com.dream.payroll.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthSalaryQuery {

    private String employeeId;
    private String name;
    private int year;
    private int month;

}

package com.dream.payroll.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkQuery {

    private String name;
    private Integer grade;
    private BigDecimal baseSalary;

}

package com.dream.payroll.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkType {

    @ExcelProperty(index = 0)
    private String typeName;
    @ExcelProperty(index = 1)
    private Integer grade;
    @ExcelProperty(index = 2)
    private BigDecimal baseSalary;

}

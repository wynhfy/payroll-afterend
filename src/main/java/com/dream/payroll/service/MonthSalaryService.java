package com.dream.payroll.service;

import com.dream.payroll.entity.MonthSalary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.entity.vo.MonthSalaryQuery;
import com.dream.payroll.result.Result;

/**
 * <p>
 * 月工资表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-19
 */
public interface MonthSalaryService extends IService<MonthSalary> {

    Result caculate(String employeeId);
}

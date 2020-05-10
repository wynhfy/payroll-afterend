package com.dream.payroll.service;

import com.dream.payroll.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.entity.vo.EmployeeVo;

/**
 * <p>
 * 员工 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
public interface EmployeeService extends IService<Employee> {


    boolean saveEmployeeAndAddress(EmployeeVo employeeVo);

    EmployeeVo getEmployeeVoById(String id);

    void updateEmployeeAndAddress(EmployeeVo employeeVo);

    void removeEmployeeAndAddress(String id);
}

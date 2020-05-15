package com.dream.payroll.controller.front;


import com.dream.payroll.entity.Address;
import com.dream.payroll.entity.Department;
import com.dream.payroll.entity.Employee;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AddressService;
import com.dream.payroll.service.DepartmentService;
import com.dream.payroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payroll/center")
@CrossOrigin
public class PersonalCenterController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("getInfo/{id}")
    public Result getInfo(@PathVariable("id") String id){
        System.out.println(id);
        Employee employee=employeeService.getById(id);
        Address address=addressService.getById(id);
        String deptId=employee.getDeptId();
        Department department=departmentService.getById(deptId);
        String deptName=department.getDeptName();
        Map<String,Object> maps=new HashMap<>();
        maps.put("user",employee);
        maps.put("address",address);
        maps.put("deptName",deptName);
        return Result.ok().data(maps);
    }


}

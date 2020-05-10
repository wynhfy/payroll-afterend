package com.dream.payroll.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.Address;
import com.dream.payroll.entity.Employee;
import com.dream.payroll.entity.vo.EmployeeQuery;
import com.dream.payroll.entity.vo.EmployeeVo;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AddressService;
import com.dream.payroll.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@RestController
@RequestMapping("/payroll/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    /**
     * 根据条件分页查询员工信息
     * @param current
     * @param limit
     * @param employeeQuery
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody(required = false)EmployeeQuery employeeQuery){
        Page<Employee> page=new Page<>(current,limit);
        QueryWrapper wrapper=new QueryWrapper();
        String id=employeeQuery.getId();
        String name=employeeQuery.getName();
        String sex=employeeQuery.getSex();
        Integer age=employeeQuery.getAge();
        String nation=employeeQuery.getNation();
        String telephone=employeeQuery.getTelephone();
        String email=employeeQuery.getEmail();
        String academic=employeeQuery.getAcademic();
        String workId=employeeQuery.getWorkId();
        String begin=employeeQuery.getBegin();
        String end=employeeQuery.getEnd();

        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(sex)){
            wrapper.eq("sex",sex);
        }
        if(age!=null){
            wrapper.eq("age",age);
        }
        if(!StringUtils.isEmpty(nation)){
            wrapper.like("nation",nation);
        }
        if(!StringUtils.isEmpty(telephone)){
            wrapper.eq("telephone",telephone);
        }
        if(!StringUtils.isEmpty(email)){
            wrapper.eq("email",email);
        }
        if(!StringUtils.isEmpty(academic)){
            wrapper.like("academic",academic);
        }
        if(!StringUtils.isEmpty(workId)){
            wrapper.like("work_id",workId);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        employeeService.page(page,wrapper);
        long total = page.getTotal();
        List<Employee> employeeList = page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",employeeList);
        return Result.ok().data(maps);
    }

    /**
     * 根据部门id查询该部门下的员工信息
     * @param current
     * @param limit
     * @param deptId
     * @param employeeQuery
     * @return
     */
    @PostMapping("pageConditionByDeptId/{current}/{limit}/{deptId}")
    public Result pageConditionByDeptId(@PathVariable("current") long current, @PathVariable("limit") long limit,@PathVariable("deptId") String deptId ,@RequestBody(required = false)EmployeeQuery employeeQuery){
        Page<Employee> page=new Page<>(current,limit);
        QueryWrapper wrapper=new QueryWrapper();
        String id=employeeQuery.getId();
        String name=employeeQuery.getName();
        String sex=employeeQuery.getSex();
        Integer age=employeeQuery.getAge();
        String nation=employeeQuery.getNation();
        String telephone=employeeQuery.getTelephone();
        String email=employeeQuery.getEmail();
        String academic=employeeQuery.getAcademic();
        String begin=employeeQuery.getBegin();
        String end=employeeQuery.getEnd();

        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(sex)){
            wrapper.eq("sex",sex);
        }
        if(age!=null){
            wrapper.eq("age",age);
        }
        if(!StringUtils.isEmpty(nation)){
            wrapper.like("nation",nation);
        }
        if(!StringUtils.isEmpty(telephone)){
            wrapper.eq("telephone",telephone);
        }
        if(!StringUtils.isEmpty(email)){
            wrapper.eq("email",email);
        }
        if(!StringUtils.isEmpty(academic)){
            wrapper.like("academic",academic);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        wrapper.eq("dept_id",deptId);
        employeeService.page(page,wrapper);
        long total = page.getTotal();
        List<Employee> employeeList = page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",employeeList);
        return Result.ok().data(maps);
    }


    /**
     * 添加员工信息和员工住址信息
     * @param employeeVo
     * @return
     */
    @PostMapping("addEmployee")
    public Result addEmployee(@RequestBody EmployeeVo employeeVo){
       boolean flag=employeeService.saveEmployeeAndAddress(employeeVo);
       if(flag){
           return Result.ok();
       }else {
           return Result.error();
       }
    }

    /**
     * 根据id获得员工信息,获取住址信息
     * @param id
     * @return
     */
    @GetMapping("getEmployeeInfo/{id}")
    public Result getEmployeeInfo(@PathVariable("id") String id){
        EmployeeVo employeeVo=employeeService.getEmployeeVoById(id);
        return Result.ok().data("employeeVo",employeeVo);
    }

    /**
     * 更新员工信息,更新员工住址信息
     * @param employeeVo
     * @return
     */
    @PostMapping("updateEmployee")
    public Result updateEmployee(@RequestBody EmployeeVo employeeVo){
        employeeService.updateEmployeeAndAddress(employeeVo);
        return Result.ok();
    }

    /**
     * 根据id删除员工信息和员工住址信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result removeEmployee(@PathVariable("id") String id){
        employeeService.removeEmployeeAndAddress(id);
        return Result.ok();
    }


}


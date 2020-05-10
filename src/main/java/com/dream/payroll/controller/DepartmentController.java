package com.dream.payroll.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.Department;
import com.dream.payroll.entity.vo.DepartmentQuery;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@RestController
@RequestMapping("/payroll/department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 查询所有部门的信息
     * @return
     */
    @GetMapping("getAllDepartment")
    public Result getAllDepartment(){
        List<Department> list=departmentService.list(null);
        return Result.ok().data("list",list);
    }

    /**
     * 分页查询所有部门信息
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageDepartment/{current}/{limit}")
    public Result pageDepartment(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<Department> page=new Page<>(current,limit);
        departmentService.page(page,null);
        long total=page.getTotal();
        List<Department> records=page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",records);
        return Result.ok().data(maps);
    }

    /**
     * 根据条件分页查询所有部门信息
     * @param current
     * @param limit
     * @param departmentQuery
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody(required = false)DepartmentQuery departmentQuery){
        Page<Department> departmentPage=new Page<>(current,limit);
        QueryWrapper<Department> queryWrapper=new QueryWrapper<>();
        String deptName=departmentQuery.getDeptName();
        String employeeId=departmentQuery.getEmployeeId();
        String begin=departmentQuery.getBegin();
        String end=departmentQuery.getEnd();
        if(!StringUtils.isEmpty(deptName)){
            queryWrapper.like("dept_name",deptName);
        }
        if(!StringUtils.isEmpty(employeeId)){
            queryWrapper.eq("employee_id",employeeId);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_modefied",end);
        }
        queryWrapper.orderByDesc("gmt_create");
        departmentService.page(departmentPage,queryWrapper);
        long total=departmentPage.getTotal();
        List<Department> recoreds=departmentPage.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",recoreds);
        return Result.ok().data(maps);
    }


    /**
     * 添加部门
     * @param department
     * @return
     */
    @PostMapping("addDepartment")
    public Result addDepartment(@RequestBody Department department){
        boolean save=departmentService.save(department);
        if(save){
            return Result.ok();
        }else{
            return  Result.error();
        }
    }


    /**
     * 根据id查询部门信息
     * @param id
     * @return
     */
    @GetMapping("getDepartmentInfoById/{id}")
    public Result getDepartmentInfoById(@PathVariable("id") String id){
        Department department=departmentService.getById(id);
        return Result.ok().data("department",department);
    }

    /**
     * 修改部门信息
     * @param department
     * @return
     */
    @PostMapping("updateDepartment")
    public Result updateDepartment(@RequestBody Department department){
        boolean flag=departmentService.updateById(department);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    @DeleteMapping("{id}")
    public Result deleteDepartment(@PathVariable("id") String id){
        Department department=departmentService.getById(id);
        Integer totalNum=department.getTotalNum();
        if(totalNum!=0){
            throw new PayRollException(20001,"部门人数不为0，不能删除");
        }
        boolean flag=departmentService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}


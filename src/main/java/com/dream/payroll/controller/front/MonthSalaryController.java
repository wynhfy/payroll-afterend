package com.dream.payroll.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.MonthSalary;
import com.dream.payroll.entity.vo.MonthSalaryQuery;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.MonthSalaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;

/**
 * <p>
 * 月工资表 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/payroll/month-salary")
@CrossOrigin
public class MonthSalaryController {

    @Autowired
    private MonthSalaryService monthSalaryService;


    /**
     * 分页查询工资
     * @param current
     * @param limit
     * @param monthSalaryQuery
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody(required = false)MonthSalaryQuery monthSalaryQuery){
        Page<MonthSalary> page=new Page<>(current,limit);
        QueryWrapper<MonthSalary> wrapper=new QueryWrapper<>();
        String employeeId=monthSalaryQuery.getEmployeeId();
        Integer year=monthSalaryQuery.getYear();
        Integer month=monthSalaryQuery.getMonth();
        if(!StringUtils.isEmpty(employeeId)){
            wrapper.eq("employee_id",employeeId);
        }
        if(year!=null && year!=0){
            wrapper.eq("year",year);
        }
        if(month!=null && month!=0){
            wrapper.eq("month",month);
        }
        wrapper.orderByDesc("year").orderByDesc("month");
        monthSalaryService.page(page,wrapper);
        long total=page.getTotal();
        List<MonthSalary> list=page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",list);
        return Result.ok().data(maps);
    }

    /**
     * 更新工资表，每次查询前调用
     * @param employeeId
     * @return
     */
    @GetMapping("caculateMonthSalary/{employeeId}")
    public Result caculateMonthSalary(@PathVariable("employeeId") String employeeId){
        Result result=monthSalaryService.caculate(employeeId);
        return result;
    }

}


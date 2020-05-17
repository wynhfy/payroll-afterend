package com.dream.payroll.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.OverWork;
import com.dream.payroll.entity.vo.OverWorkQuery;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.OverWorkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 加班记录表 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
@RestController
@RequestMapping("/payroll/over-work")
@CrossOrigin
public class OverWorkController {

    @Autowired
    private OverWorkService overWorkService;

    /**
     * 根据条件查询加班记录
     * @param current
     * @param limit
     * @param overWorkQuery
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result getOverWorkList(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody(required = false)OverWorkQuery overWorkQuery){
        Page<OverWork> page=new Page<>(current,limit);
        QueryWrapper<OverWork> wrapper=new QueryWrapper<>();
        String employeeId=overWorkQuery.getEmployeeId();
        String status=overWorkQuery.getStatus();
        String typeId=overWorkQuery.getTypeId();
        String begin=overWorkQuery.getBegin();
        String end=overWorkQuery.getEnd();
        if(!StringUtils.isEmpty(employeeId)){
            wrapper.eq("employee_id",employeeId);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(typeId)){
            wrapper.eq("type_id",typeId);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_modified");
        overWorkService.page(page,wrapper);
        long total=page.getTotal();
        List<OverWork> list=page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",list);
        return Result.ok().data(maps);
    }

    /**
     * 开始加班打卡或结束加班打卡
     * @param overWork
     * @return
     */
    @PostMapping("addOrUpdateOverWork")
    public Result addOrUpdateOverWork(@RequestBody OverWork overWork){
        System.out.println(overWork);
        String employeeId=overWork.getEmployeeId();
        String status=overWork.getStatus();
        if(StringUtils.isEmpty(employeeId) || StringUtils.isEmpty(status)){
            return Result.error().message("参数为空");
        }
        Result result=overWorkService.saveOverWork(overWork);
        return result;
    }

    /**
     * 获取加班记录信息
     * @param id
     * @return
     */
    @GetMapping("getOverWorkInfo/{id}")
    public Result getOverWorkInfo(@PathVariable("id") String id){
        OverWork overWork=overWorkService.getById(id);
        System.out.println("=============================================================="+overWork.toString());
        return Result.ok().data("overWork",overWork);
    }


}


package com.dream.payroll.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.Attendance;
import com.dream.payroll.entity.vo.AttendanceVo;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AttendanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/payroll/attendance")
@CrossOrigin
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 条件查询（不根据status来进行的查询）
     * @param current
     * @param limit
     * @param attendanceVo
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result getList(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody(required = false) AttendanceVo attendanceVo){
        Page<Attendance> page=new Page<>(current,limit);
        QueryWrapper<Attendance> wrapper=new QueryWrapper<>();
        String employeeId=attendanceVo.getEmployeeId();
        String status=attendanceVo.getStatus();
        String begin=attendanceVo.getBegin();
        String end=attendanceVo.getEnd();
        if(!StringUtils.isEmpty(employeeId)){
            wrapper.eq("employee_id",employeeId);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        attendanceService.page(page,wrapper);
        long total=page.getTotal();
        List<Attendance> list=page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",list);
        return Result.ok().data(maps);
    }

    @GetMapping("add/{employeeId}")
    public Result addAttendance(@PathVariable("employeeId") String employeeId){
        Result result=attendanceService.saveAttendance(employeeId);
        return result;
    }

    @DeleteMapping("{id}")
    public Result remove(@PathVariable("id") String id){
        attendanceService.removeById(id);
        return Result.ok();
    }

    @PostMapping("removeByBatch")
    public Result removeByBatch(@RequestBody String attendances){
        List<Attendance> list=(List<Attendance>) JSONArray.parseArray(attendances,Attendance.class);
        List<String> ids=new ArrayList<>();
        for(Attendance attendance:list){
            ids.add(attendance.getId());
        }
        attendanceService.removeByIds(ids);
        return Result.ok();
    }


}


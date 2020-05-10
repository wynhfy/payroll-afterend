package com.dream.payroll.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.Work;
import com.dream.payroll.entity.vo.WorkQuery;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.WorkService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 工种表 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@RestController
@RequestMapping("/payroll/work")
@CrossOrigin
public class WorkController {

    @Autowired
    private WorkService workService;

    @PostMapping("addWorkType")
    public Result addWorkType(MultipartFile file){
        workService.saveWorkType(file,workService);
        return Result.ok();
    }

    @GetMapping("getAllWork")
    public Result getAllWork(){
        List<Work> workList=workService.list(null);
        return Result.ok().data("items",workList);
    }

    @PostMapping("condition")
    public Result condition(@RequestBody WorkQuery workQuery){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        String name=workQuery.getName();
        Integer grade=workQuery.getGrade();
        BigDecimal baseSalary=workQuery.getBaseSalary();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(grade!=null){
            wrapper.eq("grade",grade);
        }
        if(baseSalary!=null){
            wrapper.eq("base_salary",baseSalary);
        }
        List<Work> workList=workService.list(wrapper);
        return Result.ok().data("items",workList);
    }


    @DeleteMapping("{id}")
    public Result removeWork(@PathVariable("id") String id){
        boolean flag=workService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @PostMapping("updateWork")
    public Result updateWork(@RequestBody Work work){
        boolean flag=workService.updateById(work);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("getWorkInfo/{id}")
    public Result getWorkInfo(@PathVariable("id") String id){
        Work work=workService.getById(id);
        return Result.ok().data("work",work);
    }


}


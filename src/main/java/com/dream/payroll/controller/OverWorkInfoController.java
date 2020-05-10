package com.dream.payroll.controller;


import com.dream.payroll.entity.OverWorkInfo;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.OverWorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 加班类别信息表 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-10
 */
@RestController
@RequestMapping("/payroll/over-work-info")
@CrossOrigin
public class OverWorkInfoController {

    @Autowired
    private OverWorkInfoService overWorkInfoService;

    /**
     * 添加加班类别信息
     * @param overWorkInfo
     * @return
     */
    @PostMapping("addOverWorkInfo")
    public Result addOverWorkInfo(@RequestBody OverWorkInfo overWorkInfo){
        boolean flag=overWorkInfoService.save(overWorkInfo);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("getOverWorkInfoList")
    public Result getOverWorkInfoList(){
        List<OverWorkInfo> list=overWorkInfoService.list(null);
        return Result.ok().data("items",list);
    }

    @GetMapping("getOverWorkInfoById/{id}")
    public Result getOverWorkInfoById(@PathVariable("id") String id){
         OverWorkInfo overWorkInfo=overWorkInfoService.getById(id);
         return Result.ok().data("overWorkInfo",overWorkInfo);
    }

    @PostMapping("updateOverWorkInfo")
    public Result updateOverWorkInfo(@RequestBody OverWorkInfo overWorkInfo){
        boolean flag=overWorkInfoService.updateById(overWorkInfo);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    public Result removeOverWorkInfo(@PathVariable("id") String id){
        boolean flag=overWorkInfoService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}


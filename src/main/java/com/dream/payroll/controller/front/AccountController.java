package com.dream.payroll.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.Account;
import com.dream.payroll.entity.vo.AccountVo;
import com.dream.payroll.entity.vo.UserInfo;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AccountService;
import com.dream.payroll.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器  前台登录注册接口
 * </p>
 *
 * @author wyn
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/payroll/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    public Result login(@RequestBody AccountVo accountVo){
        String token=accountService.login(accountVo);
        System.out.println(token);
        return Result.ok().data("token",token);
    }

    @GetMapping("getInfo")
    public Result getInfo(HttpServletRequest request){
        UserInfo userInfo=accountService.getInfo(request);
        System.out.println(userInfo);
        return Result.ok().data("userInfo",userInfo);
    }

    @PostMapping("register")
    public Result register(@RequestBody AccountVo accountVo){
        accountService.register(accountVo);
        return Result.ok();
    }


    /**
     * 条件查询账号
     * @param current
     * @param limit
     * @param accountVo
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result getList(@PathVariable("current") long current,@PathVariable("limit") long limit,@RequestBody(required = false) AccountVo accountVo){
        Page<Account> page=new Page<>(current,limit);
        QueryWrapper<Account> wrapper=new QueryWrapper<>();
        String employeeId=accountVo.getEmployeeId();
        String mobile=accountVo.getMobile();
        if(!StringUtils.isEmpty(employeeId)){
            wrapper.eq("employee_id",employeeId);
        }
        if(!StringUtils.isEmpty(mobile)){
            wrapper.eq("mobile",mobile);
        }
        accountService.page(page,wrapper);
        long total=page.getTotal();
        List<Account> list=page.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",list);
        return Result.ok().data(maps);
    }


    @GetMapping("forbid/{id}")
    public Result forbidden(@PathVariable("id") String id){
        Account account=accountService.getById(id);
        account.setIsDisabled(true);
        accountService.updateById(account);
        return Result.ok();
    }

    @GetMapping("unforbid/{id}")
    public Result unforbidden(@PathVariable("id") String id){
        Account account=accountService.getById(id);
        account.setIsDisabled(false);
        accountService.updateById(account);
        return Result.ok();
    }

}


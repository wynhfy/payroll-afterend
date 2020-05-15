package com.dream.payroll.controller;


import com.dream.payroll.entity.Account;
import com.dream.payroll.entity.vo.AccountVo;
import com.dream.payroll.entity.vo.UserInfo;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AccountService;
import com.dream.payroll.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
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


}


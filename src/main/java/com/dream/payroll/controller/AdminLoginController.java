package com.dream.payroll.controller;

import com.dream.payroll.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/user")
@CrossOrigin
public class AdminLoginController {


    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("info")
    public Result getInfo(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}

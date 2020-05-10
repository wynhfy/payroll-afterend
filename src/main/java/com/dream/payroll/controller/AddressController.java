package com.dream.payroll.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.payroll.entity.Address;
import com.dream.payroll.entity.vo.AddressQuery;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地址 前端控制器
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@RestController
@RequestMapping("/payroll/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 根据条件分页查询地址
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public Result pageCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) AddressQuery addressQuery){
        Page<Address> addressPage=new Page<>(current,limit);
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        String id=addressQuery.getId();
        String province=addressQuery.getProvince();
        String city=addressQuery.getCity();
        String area=addressQuery.getArea();
        String detail=addressQuery.getDetail();
        if(!StringUtils.isEmpty(id)){
            queryWrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(province)){
            queryWrapper.like("province",province);
        }
        if(!StringUtils.isEmpty(city)){
            queryWrapper.like("city",city);
        }
        if(!StringUtils.isEmpty(area)){
            queryWrapper.like("area",area);
        }
        if(!StringUtils.isEmpty(detail)){
            queryWrapper.like("detail",detail);
        }
        queryWrapper.orderByDesc("gmt_create");
        addressService.page(addressPage,queryWrapper);
        long total=addressPage.getTotal();
        List<Address> addressList=addressPage.getRecords();
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("items",addressList);
        return Result.ok().data(maps);
    }

    /**
     * 添加住址信息
     * @return
     */
    @PostMapping("addAddress")
    public Result addAddress(@RequestBody Address address){
        boolean save=addressService.save(address);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据工号查询住址
     * @param id
     * @return
     */
    @GetMapping("getAddressById/{id}")
    public Result getAddressById(@PathVariable("id") String id){
        Address address=addressService.getById(id);
        return Result.ok().data("address",address);
    }

    /**
     * 根据工号更新住址信息
     * @param address
     * @return
     */
    @PostMapping("updateAddress")
    public Result updateAddress(@RequestBody Address address){
        boolean flag=addressService.updateById(address);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据工号删除地址信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result removeAddress(@PathVariable("id") String id){
        boolean flag=addressService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}


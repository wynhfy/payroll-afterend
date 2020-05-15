package com.dream.payroll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.Account;
import com.dream.payroll.entity.Employee;
import com.dream.payroll.entity.vo.AccountVo;
import com.dream.payroll.entity.vo.UserInfo;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.AccountMapper;
import com.dream.payroll.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.payroll.service.EmployeeService;
import com.dream.payroll.util.JwtUtils;
import com.dream.payroll.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-15
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void register(AccountVo accountVo) {
        String employeeId=accountVo.getEmployeeId();
        String mobile=accountVo.getMobile();
        String code=accountVo.getCode();
        String password=accountVo.getPassword();
        if(StringUtils.isEmpty(employeeId) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)){
            throw new PayRollException(20001,"工号 手机号 验证码 密码不能为空");
        }
        //获取验证码
        String checkcode=redisTemplate.opsForValue().get(mobile);
        if(!checkcode.equals(code)){
            throw new PayRollException(20001,"输入验证码错误");
        }

        QueryWrapper<Account> wrapperEmployeeId=new QueryWrapper<>();
        wrapperEmployeeId.eq("employee_id",employeeId);
        Integer count = baseMapper.selectCount(wrapperEmployeeId);
        if(count.intValue()>0){
            throw new PayRollException(20001,"该工号已被激活，请不要重复注册");
        }
        QueryWrapper<Account> wrapperMobile=new QueryWrapper<>();
        wrapperMobile.eq("mobile",mobile);
        count=baseMapper.selectCount(wrapperMobile);
        if(count>0){
            throw new PayRollException(20001,"该手机号已被绑定，请更换手机号");
        }
        //插入一个账号
        Account account=new Account();
        account.setEmployeeId(employeeId);
        account.setMobile(mobile);
        account.setPassword(MD5.encrypt(password));
        account.setIsDisabled(false);
        baseMapper.insert(account);
        //修改员工信息里的is_active
        Employee employee=employeeService.getById(employeeId);
        employee.setIsActive(true);
        employeeService.updateById(employee);
    }

    @Override
    public String login(AccountVo accountVo) {
        String mobile=accountVo.getMobile();
        String password=accountVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw  new PayRollException(20001,"手机号和密码不能为空");
        }
        QueryWrapper<Account> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Account account = baseMapper.selectOne(wrapper);
        if(account==null){
            throw new PayRollException(20001,"查不到该手机号，该手机号还未绑定");
        }
        if(!MD5.encrypt(password).equals(account.getPassword())){
            throw new PayRollException(20001,"密码输入错误");
        }
        if(account.getIsDisabled()){
            throw new PayRollException(20001,"该用户被禁用");
        }
        Employee employee=employeeService.getById(account.getEmployeeId());
        String token= JwtUtils.getJwtToken(account.getId(),employee.getName());
        return token;
    }

    @Override
    public UserInfo getInfo(HttpServletRequest request) {
        String id=JwtUtils.getMemberIdByJwtToken(request);
        Account account=baseMapper.selectById(id);
        String employeeId=account.getEmployeeId();
        Employee employee=employeeService.getById(employeeId);
        UserInfo userInfo=new UserInfo();
        userInfo.setId(account.getId());
        userInfo.setEmployeeId(employeeId);
        userInfo.setMobile(account.getMobile());
        userInfo.setName(employee.getName());
        return userInfo;
    }
}

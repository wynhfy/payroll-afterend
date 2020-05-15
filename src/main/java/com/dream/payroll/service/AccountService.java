package com.dream.payroll.service;

import com.dream.payroll.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.entity.vo.AccountVo;
import com.dream.payroll.entity.vo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-15
 */
public interface AccountService extends IService<Account> {

    void register(AccountVo accountVo);

    String login(AccountVo accountVo);

    UserInfo getInfo(HttpServletRequest request);
}

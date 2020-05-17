package com.dream.payroll.service;

import com.dream.payroll.entity.Benefit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.entity.OverWork;

/**
 * <p>
 * 津贴表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
public interface BenefitService extends IService<Benefit> {

    void updateBendfit(OverWork overWork);

}

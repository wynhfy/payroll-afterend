package com.dream.payroll.service;

import com.dream.payroll.entity.OverWork;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.result.Result;

/**
 * <p>
 * 加班记录表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
public interface OverWorkService extends IService<OverWork> {

    Result saveOverWork(OverWork overWork);
}

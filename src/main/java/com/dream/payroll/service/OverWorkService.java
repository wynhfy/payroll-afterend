package com.dream.payroll.service;

import com.dream.payroll.entity.OverWork;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 加班记录表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
public interface OverWorkService extends IService<OverWork> {

    boolean saveOverWork(OverWork overWork);
}

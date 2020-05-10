package com.dream.payroll.service;

import com.dream.payroll.entity.Work;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 工种表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
public interface WorkService extends IService<Work> {

    void saveWorkType(MultipartFile file,WorkService workService);
}

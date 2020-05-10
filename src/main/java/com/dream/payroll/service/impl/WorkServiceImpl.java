package com.dream.payroll.service.impl;

import com.alibaba.excel.EasyExcel;
import com.dream.payroll.entity.Work;
import com.dream.payroll.entity.excel.WorkType;
import com.dream.payroll.listener.WorkExcelListener;
import com.dream.payroll.mapper.WorkMapper;
import com.dream.payroll.service.WorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 工种表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {

    /**
     * 将excel里的数据存入数据库
     * @param file
     * @param workService
     */
    @Override
    public void saveWorkType(MultipartFile file,WorkService workService) {
        try{
            InputStream in=file.getInputStream();
            EasyExcel.read(in, WorkType.class,new WorkExcelListener(workService)).sheet().doRead();
        }catch (Exception e){

        }
    }
}

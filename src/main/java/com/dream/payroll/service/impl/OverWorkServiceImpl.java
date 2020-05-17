package com.dream.payroll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.OverWork;
import com.dream.payroll.entity.OverWorkInfo;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.OverWorkMapper;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.BenefitService;
import com.dream.payroll.service.OverWorkInfoService;
import com.dream.payroll.service.OverWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 加班记录表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
@Service
public class OverWorkServiceImpl extends ServiceImpl<OverWorkMapper, OverWork> implements OverWorkService {

    @Autowired
    private OverWorkInfoService overWorkInfoService;

    @Autowired
    private BenefitService benefitService;

    // TODO 更新津贴表

    /**
     * 同时更新加班表和津贴表
     * @param overWork
     * @return
     */
    @Override
    public Result saveOverWork(OverWork overWork) {
        String employeeId=overWork.getEmployeeId();
        String status=overWork.getStatus();
        String typeId=overWork.getTypeId();
        System.out.println(typeId);

        OverWorkInfo overWorkInfo=overWorkInfoService.getById(typeId);
        String description=overWorkInfo.getDescription();
        overWork.setDescription(description);

        QueryWrapper<OverWork> wrapper=new QueryWrapper<>();
        //若是开始加班，要判断之前是否已经结束加班
        if(status.equals("开始加班")){
            wrapper.eq("status","开始加班");
            wrapper.eq("employee_id",employeeId);
            Integer count = baseMapper.selectCount(wrapper);
            if(count.intValue()>0){
                //throw new PayRollException(20001,"上次加班还未结束，请勿重复填写");
                return Result.ok().data("error","上次加班还未结束，请勿重复填写");
            }else{
                boolean save = this.save(overWork);
                if(!save){
                    throw new PayRollException(20001,"添加加班记录失败");
                }

            }
        }else if(status.equals("结束加班")){                        //结束加班的同时更新津贴表
            wrapper.eq("status","开始加班");
            wrapper.eq("employee_id",employeeId);
            OverWork oldOverWork=baseMapper.selectOne(wrapper);
            oldOverWork.setStatus("结束加班");
            boolean update = this.updateById(oldOverWork);
            if(!update){
                throw new PayRollException(20001,"更新加班记录失败");
            }
            //更新津贴表
            benefitService.updateBendfit(oldOverWork);
        }
        return Result.ok();
    }




}

package com.dream.payroll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.OverWork;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.OverWorkMapper;
import com.dream.payroll.service.OverWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    // TODO 更新津贴表
    @Override
    public boolean saveOverWork(OverWork overWork) {
        String employeeId=overWork.getEmployeeId();
        String status=overWork.getStatus();
        QueryWrapper<OverWork> wrapper=new QueryWrapper<>();
        //若是开始加班，要判断之前是否已经结束加班
        if(status.equals("开始加班")){
            wrapper.eq("status","开始加班");
            wrapper.eq("employee_id",employeeId);
            Integer count = baseMapper.selectCount(wrapper);
            if(count.intValue()>0){
                throw new PayRollException(20001,"上次加班还未结束，请勿重复填写");
            }else{
                boolean save = this.save(overWork);
                if(!save){
                    throw new PayRollException(20001,"添加加班记录失败");
                }
            }
        }else if(status.equals("结束加班")){
            wrapper.eq("status","开始加班");
            wrapper.eq("employee_id",employeeId);
            OverWork oldOverWork=baseMapper.selectOne(wrapper);
            oldOverWork.setStatus("结束加班");
            boolean update = this.updateById(oldOverWork);
            if(!update){
                throw new PayRollException(20001,"更新加班记录失败");
            }
        }
        return true;
    }
}

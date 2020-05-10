package com.dream.payroll.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.Work;
import com.dream.payroll.entity.excel.WorkType;
import com.dream.payroll.service.WorkService;

public class WorkExcelListener extends AnalysisEventListener<WorkType> {

    private WorkService workService;

    public WorkExcelListener(){

    }

    public WorkExcelListener(WorkService workService){
        this.workService=workService;
    }

    @Override
    public void invoke(WorkType workType, AnalysisContext analysisContext) {
        if(workType==null){
            System.out.println("文件数据为空");
        }
        Work work=this.isExist(workService,workType.getTypeName());
        //不存在则添加进去
        if(work==null){
            work=new Work();
            work.setName(workType.getTypeName());
            work.setGrade(workType.getGrade());
            work.setBaseSalary(workType.getBaseSalary());
            workService.save(work);
        }

    }

    //判断该工种是否已经存在
    private Work isExist(WorkService workService,String name){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        Work work=workService.getOne(wrapper);
        return work;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

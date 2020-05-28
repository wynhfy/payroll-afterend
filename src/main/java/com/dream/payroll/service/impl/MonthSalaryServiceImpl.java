package com.dream.payroll.service.impl;

import com.dream.payroll.entity.*;
import com.dream.payroll.entity.vo.MonthSalaryQuery;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.AttendanceMapper;
import com.dream.payroll.mapper.BenefitMapper;
import com.dream.payroll.mapper.MonthSalaryMapper;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AttendanceService;
import com.dream.payroll.service.EmployeeService;
import com.dream.payroll.service.MonthSalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.payroll.service.WorkService;
import com.dream.payroll.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;

/**
 * <p>
 * 月工资表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-19
 */
@Service
public class MonthSalaryServiceImpl extends ServiceImpl<MonthSalaryMapper, MonthSalary> implements MonthSalaryService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WorkService workService;

    @Autowired
    private MonthSalaryMapper mapper;

    @Autowired
    private BenefitMapper benefitMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;



    @Override
    public Result caculate(String employeeId) {
        LocalDateTime now=LocalDateTime.now();
        int year=now.getYear();
        int month=now.getMonthValue();
        Calendar calendar=Calendar.getInstance();
        //得到这个月的天数用于考勤打卡计算
        int days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String datetime=null;
        if(month<10){
            datetime=year+"-0"+month;
        }else{
            datetime=year+"-"+month;
        }

        MonthSalary monthSalary=mapper.selectByDateTime(year,month,employeeId);
        if(monthSalary==null){              //如果没有这个月的数据
            monthSalary=new MonthSalary();
            //设置工号
            monthSalary.setEmployeeId(employeeId);
            //设置姓名
            Employee employee=employeeService.getById(employeeId);
            monthSalary.setName(employee.getName());
            //设置底薪
            String workId=employee.getWorkId();
            Work work=workService.getById(workId);
            monthSalary.setBaseSalary(work.getBaseSalary());
            //设置津贴
            List<Benefit> benefitList=benefitMapper.getBenefitByIdAndDate(employeeId,datetime);
            if(benefitList.size()>0){
                Benefit benefit=benefitList.get(0);
                monthSalary.setBenefit(benefit.getBenefitMoney());
            }else{
                monthSalary.setBenefit(new BigDecimal(0.0));        //如果没有加班，则津贴设置为0
            }

            //设置扣的钱
            List<Attendance> list=attendanceMapper.selectCount(datetime,employeeId);    //查询出这个月目前总共的考勤次数
            int arrivedays=list.size();
            int latedays=0;
            int absentdays=0;
            for(Attendance attendance:list){
                if(attendance.getStatus().equals("迟到")){
                    latedays++;                 //迟到的天数,迟到扣10块
                }
            }
            int dayOfMonth=now.getDayOfMonth(); //这是这个月的第几天
            int dayOfWeekend= DateUtil.weekendNumber(new Date());
            absentdays=dayOfMonth-arrivedays-dayOfWeekend;   //缺席的天数,缺勤扣50块

            BigDecimal lateMoney=new BigDecimal(10).multiply(new BigDecimal(latedays));
            BigDecimal absentMoney=new BigDecimal(50).multiply(new BigDecimal(absentdays));
            BigDecimal deductMoney=lateMoney.add(absentMoney);
            monthSalary.setDeductMoney(deductMoney);

            monthSalary.setFinalMoney(monthSalary.getBaseSalary().add(monthSalary.getBenefit()).subtract(monthSalary.getDeductMoney()));
            monthSalary.setYear(year);
            monthSalary.setMonth(month);

            boolean save = this.save(monthSalary);
            if(save){
                return Result.ok();
            }else{
                throw new PayRollException(20001,"添加月工资记录失败");
            }

        }else{
            //已经有了这个月的数据的时候,只用更新津贴、扣费和最终工资这三项
            List<Benefit> benefitList=benefitMapper.getBenefitByIdAndDate(employeeId,datetime);
            if(benefitList.size()>0){
                Benefit benefit=benefitList.get(0);
                monthSalary.setBenefit(benefit.getBenefitMoney());
            }

            List<Attendance> list=attendanceMapper.selectCount(datetime,employeeId);
            int arrivedays=list.size();
            int latedays=0;
            int absentdays=0;
            for(Attendance attendance:list){
                if(attendance.getStatus().equals("迟到")){
                    latedays++;
                }
            }
            int dayOfMonth=now.getDayOfMonth(); //这是这个月的第几天
            int dayOfWeekend= DateUtil.weekendNumber(new Date());
            absentdays=dayOfMonth-arrivedays-dayOfWeekend;   //缺席的天数,缺勤扣50块

            BigDecimal lateMoney=new BigDecimal(10).multiply(new BigDecimal(latedays));
            BigDecimal absentMoney=new BigDecimal(50).multiply(new BigDecimal(absentdays));
            BigDecimal deductMoney=lateMoney.add(absentMoney);
            monthSalary.setDeductMoney(deductMoney);

            monthSalary.setFinalMoney(monthSalary.getBaseSalary().add(monthSalary.getBenefit()).subtract(monthSalary.getDeductMoney()));
            monthSalary.setYear(year);
            monthSalary.setMonth(month);

            boolean update = this.updateById(monthSalary);
            if(update){
                return Result.ok();
            }else{
                throw new PayRollException(20001,"更新月工资记录失败");
            }

        }
    }


}

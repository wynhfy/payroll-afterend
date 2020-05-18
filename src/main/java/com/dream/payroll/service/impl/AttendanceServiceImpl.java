package com.dream.payroll.service.impl;

import com.dream.payroll.entity.Attendance;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.AttendanceMapper;
import com.dream.payroll.result.Result;
import com.dream.payroll.service.AttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-18
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Autowired
    private AttendanceMapper mapper;

    @Override
    public Result saveAttendance(String employeeId) {
        LocalDateTime now=LocalDateTime.now();
        String datetime=null;
        int year=now.getYear(); int month=now.getMonthValue(); int day=now.getDayOfMonth();
        if(month<10){
            datetime=year+"-0"+month;
        }else{
            datetime=year+"-"+month;
        }
        if(day<10){
            datetime+="-0"+day;
        }else{
            datetime+="-"+day;
        }
        Attendance attendance=mapper.selectByDateTime(datetime,employeeId);
        if(attendance!=null){
            return Result.ok().data("message","今天已经打过卡啦，请勿重复打卡");
        }

        attendance=new Attendance();
        attendance.setEmployeeId(employeeId);

        //得到打卡时的时间
        LocalTime nowTime=LocalTime.of(now.getHour(),now.getMinute());
        if(nowTime.isBefore(LocalTime.of(8,30))){   //8点30前打卡则不算迟到
            attendance.setStatus("已到");
        }else{
            attendance.setStatus("迟到");
        }
        int insert = baseMapper.insert(attendance);
        if(insert>0){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

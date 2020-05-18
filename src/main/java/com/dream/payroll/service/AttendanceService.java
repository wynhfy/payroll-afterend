package com.dream.payroll.service;

import com.dream.payroll.entity.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.payroll.result.Result;

/**
 * <p>
 * 考勤表 服务类
 * </p>
 *
 * @author wyn
 * @since 2020-05-18
 */
public interface AttendanceService extends IService<Attendance> {

    Result saveAttendance(String employeeId);
}

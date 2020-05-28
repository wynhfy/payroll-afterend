package com.dream.payroll.mapper;

import com.dream.payroll.entity.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author wyn
 * @since 2020-05-18
 */
public interface AttendanceMapper extends BaseMapper<Attendance> {

    Attendance selectByDateTime(@Param("datetime") String datetime, @Param("employeeId") String employeeId);

    List<Attendance> selectCount(@Param("datetime") String datetime,@Param("employeeId") String employeeId);

}

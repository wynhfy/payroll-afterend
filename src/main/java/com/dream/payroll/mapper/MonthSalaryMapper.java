package com.dream.payroll.mapper;

import com.dream.payroll.entity.MonthSalary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 月工资表 Mapper 接口
 * </p>
 *
 * @author wyn
 * @since 2020-05-19
 */
public interface MonthSalaryMapper extends BaseMapper<MonthSalary> {

    MonthSalary selectByDateTime(@Param("year") int year,@Param("month") int month , @Param("employeeId") String employeeId);

}

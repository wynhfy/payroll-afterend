package com.dream.payroll.mapper;

import com.dream.payroll.entity.Benefit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 津贴表 Mapper 接口
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
public interface BenefitMapper extends BaseMapper<Benefit> {

    List<Benefit> getBenefitByIdAndDate(String employeeId,String datetime);

}

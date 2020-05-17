package com.dream.payroll.mapper;

import com.dream.payroll.entity.OverWork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 加班记录表 Mapper 接口
 * </p>
 *
 * @author wyn
 * @since 2020-05-16
 */
public interface OverWorkMapper extends BaseMapper<OverWork> {

    List<OverWork> getOverWorkByDate(String datetime);

}

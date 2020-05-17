package com.dream.payroll.service.impl;

import com.dream.payroll.entity.Benefit;
import com.dream.payroll.entity.OverWork;
import com.dream.payroll.entity.OverWorkInfo;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.BenefitMapper;
import com.dream.payroll.service.BenefitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.payroll.service.OverWorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 津贴表 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
@Service
public class BenefitServiceImpl extends ServiceImpl<BenefitMapper, Benefit> implements BenefitService {

    private DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private BenefitMapper benefitMapper;

    @Autowired
    private OverWorkInfoService overWorkInfoService;

    @Override
    public void updateBendfit(OverWork overWork) {
        String typeId=overWork.getTypeId();
        OverWorkInfo overWorkInfo=overWorkInfoService.getById(typeId);
        BigDecimal fee = overWorkInfo.getFee();

        LocalDateTime begin=LocalDateTime.parse(formatter.format(overWork.getGmtCreate()),df);
        LocalDateTime end=LocalDateTime.parse(formatter.format(overWork.getGmtModified()),df);
        Duration duration=Duration.between(begin,end);
        long minutes=duration.toMinutes();
        double time=caculateHours(minutes);

        String datetime=null;
        int year=end.getYear(); int month=end.getMonthValue(); int day=end.getDayOfMonth();
        if(month<10){
            datetime=year+"-0"+month;
        }else{
            datetime=year+"-"+month;
        }
        List<Benefit> list=benefitMapper.getBenefitByIdAndDate(overWork.getEmployeeId(),datetime);

        //还没有某一年某一个月的津贴记录时就插入一条
        if(list.size()==0){
            Benefit benefit=new Benefit();
            benefit.setEmployeeId(overWork.getEmployeeId());
            benefit.setOverTime(time);
            benefit.setOverDay(1);
            benefit.setBenefitMoney(fee.multiply(new BigDecimal(time)));
            int insert = baseMapper.insert(benefit);
            if(insert==0){
                throw new PayRollException(20001,"添加津贴记录失败");
            }
        }else if(list.size()==1){
            Benefit benefit=list.get(0);
            LocalDateTime gmtModified=LocalDateTime.parse(formatter.format(benefit.getGmtModified()),df);
            //如果不是同一天，则加班天数+1
            if(day!=gmtModified.getDayOfMonth()){
                benefit.setOverDay(benefit.getOverDay()+1);
            }
            benefit.setOverTime(benefit.getOverTime()+time);
            benefit.setBenefitMoney(fee.multiply(new BigDecimal(time)).add(benefit.getBenefitMoney()));
            int update = baseMapper.updateById(benefit);
            if(update==0){
                throw new PayRollException(20001,"更新津贴记录失败");
            }
        }

    }

    /**
     * 以小时为单位计算加班时长，不满半小时的按半小时算，超过半小时不满一小时的按一小时算
     * @param mintues
     * @return
     */
    public static double caculateHours(long mintues){
        long hours=mintues/60;
        long rest=mintues-hours*60;
        double time;
        if(rest>30){
            time=hours+1;
        }else{
            time=hours+0.5;
        }
        return time;
    }

}

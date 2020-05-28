package com.dream.payroll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.payroll.entity.OverWork;
import com.dream.payroll.mapper.OverWorkMapper;
import com.dream.payroll.service.OverWorkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class PayrollApplicationTests {

    @Autowired
    private OverWorkService overWorkService;

    @Autowired
    private OverWorkMapper mapper;

    @Test
    void contextLoads() {
//        double price=5.5;
//        BigDecimal totalprice=new BigDecimal(10.5);
//        BigDecimal finalprice=totalprice.multiply(new BigDecimal(price));
//        System.out.println(finalprice.toString());
//
//        LocalDateTime ldt=LocalDateTime.now();
//        System.out.println(ldt.getYear()+"年"+ldt.getMonthValue()+"月");
//        String time=ldt.getYear()+"-0"+ldt.getMonthValue();
//        List<OverWork> list=mapper.getOverWorkByDate(time);
//        System.out.println(list.size());
//        for(OverWork overWork:list){
//            System.out.println(overWork);
//        }

        Calendar calendar=Calendar.getInstance();
        int days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(days);

    }

}

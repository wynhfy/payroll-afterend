package com.dream.payroll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class PayrollApplicationTests {

    @Test
    void contextLoads() {
        double price=5.5;
        BigDecimal totalprice=new BigDecimal(10.5);
        BigDecimal finalprice=totalprice.multiply(new BigDecimal(price));
        System.out.println(finalprice.toString());
    }

}

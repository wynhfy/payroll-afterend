package com.dream.payroll.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //一星期的毫秒数
    private static final long MILLISECONDOFONEWEEK = 7*24*60*60*1000;
    /**
     * 计算某月份所经过周末数
     * @param now
     * @return 星期六、日的总天数
     */
    public static int weekendNumber(Date now) {
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(now);
        int count = 0;
        int r = 0;//余数
        int firstDay = getFirstDayOfInMonth(now);
        if (firstDay == 1){
            count += calendarNow.get(Calendar.DAY_OF_MONTH)/7*2;
            count++;
        } else {
            r = calendarNow.get(Calendar.DAY_OF_MONTH)%7;
            count += calendarNow.get(Calendar.DAY_OF_MONTH)/7*2;
            if ((firstDay+r) == 8) {
                count += 1;
            }else if ( (firstDay + r)> 8){
                count += 2;
            }
        }
        return count;
    }

    /**
     * 每月的第一天是星期几。星期日返回1，星期一是2。
     * @param now
     * @return
     */
    public static int getFirstDayOfInMonth(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static void main(String[] args) {
        System.out.println(weekendNumber(new Date()));
        System.out.println(new Date());
    }

}
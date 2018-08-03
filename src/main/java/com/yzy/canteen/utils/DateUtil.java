package com.yzy.canteen.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @description: 水果的起始和终止时间
 * @author: yzy
 * @create: 2018-05-23 18:33
 */
@Slf4j
public class DateUtil {
    public static void main(String[] args) {
        String str1 = "2017-01-01";
        String str2 = "2017-01-01";
        List<String> list=getBetweenDates(str1,str2);
        System.out.println(list);
    }



    public static Date getDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date sqlDate = null;
        try {
            sqlDate = new Date(sdf.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }

    public static String getString(Date sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(sqlDate);
        return str;
    }

    public static Integer getDays(String dateStr1, String dateStr2){
        if(dateStr1.equals(dateStr2)) return 1;
        Date date1 = getDate(dateStr1);
        Date date2 = getDate(dateStr2);
        Integer days = (int) (Math.abs((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)));
        return days+1;
    }

    public static List<String> getBetweenDates(String beginStr, String endStr) {
        java.util.Date begin=getDate(beginStr);
        Date end=getDate(endStr);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime()<=end.getTime()){

            result.add(formatter.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }

        return result;
    }
}

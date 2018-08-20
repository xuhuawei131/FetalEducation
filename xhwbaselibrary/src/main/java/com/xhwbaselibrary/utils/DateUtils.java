package com.xhwbaselibrary.utils;

import java.util.Calendar;

/**
 * Created by lingdian on 17/9/26.
 */

public class DateUtils {

    public static boolean isSameDay(long time1,long time2){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(time1);

        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(time2);

        int year1=calendar1.get(Calendar.YEAR);
        int year2=calendar2.get(Calendar.YEAR);
        if (year1==year2){
            int day1=calendar1.get(Calendar.DAY_OF_YEAR);
            int day2=calendar1.get(Calendar.DAY_OF_YEAR);
            if(day1==day2){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}

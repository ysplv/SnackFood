package com.example.administrator.mysnack.utils;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class TiemTool {
    private final static long dayLevelValue = 24 * 60 * 60 * 1000;
    private final static long hourLevelValue = 60 * 60 * 1000;
    private final static long minuteLevelValue = 60 * 1000;
    private final static long secondLevelValue = 1000;
    public static String getDifference(long period) {//根据毫秒差计算时间差
        String result = null ;
        /*******计算出时间差中的年、月、日、天、时、分、秒*******/

        int day = getDay(period) ;
        int hour = getHour(period  - day*dayLevelValue) ;
        int minute = getMinute(period - day*dayLevelValue - hour*hourLevelValue) ;
        int second = getSecond(period - day*dayLevelValue - hour*hourLevelValue - minute*minuteLevelValue) ;
        result = day+"天"+hour+"小时"+minute+"分"+second+"秒";
        return result;
    }
    //计算天数
    public static int getDay(long period){
        return (int) (period/dayLevelValue);
    }
    //小时
    public static int getHour(long period){
        return (int) (period/hourLevelValue);
    }
    //分钟
    public static int getMinute(long period){
        return (int) (period/minuteLevelValue);
    }
    //秒
    public static int getSecond(long period){
        return (int) (period/secondLevelValue);
    }

}

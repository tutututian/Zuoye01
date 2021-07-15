package com.hp.date;

import java.util.Date;

/*
* 使用Date输出当前系统时间，以及3天后这一刻的时间
*
* */
public class Test01 {
    public static void main(String[] args) {
        Date date=new Date();
        System.out.println("当前时间 = " + date);
        long time = date.getTime();
        System.out.println("time = " + time);
       // date.setTime(time);
        //3天后
        long time1=time+3*60*60*24*1000;
        System.out.println("time1 = " + time1);
        date.setTime(time1);
        System.out.println("三天后时间 = " + date);
    }
}

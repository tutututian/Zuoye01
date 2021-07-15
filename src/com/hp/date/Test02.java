package com.hp.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
*  将当前系统时间以"yyyy-MM-dd HH:mm:ss"格式输出
* */
public class Test02 {
    public static void main(String[] args) {


    Date date=new Date();
        String pattern="yyyy-MM-dd HH:mm:ss";
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String chinaDateStr=sdf.format(date);
        System.out.println("chinaDateStr = " + chinaDateStr);

    }
}

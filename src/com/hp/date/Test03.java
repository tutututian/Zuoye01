package com.hp.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*
* 
* 输入某人生日，格式为"yyyy-MM-dd"，输出到现在为止经过了多少周。
* */
public class Test03 {
    public static void main(String[] args) throws ParseException {
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的生日, 输入格式为:yyyy-MM-dd");
        String birthday = sc.nextLine();
        //  matches() 方法用于检测字符串是否匹配给定的正则表达式。
        if (birthday.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}")) {
            //parse() 方法可解析一个日期时间字符串，并返回 1970/1/1 午夜距离该日期时间的毫秒数
            Date birDate = sdf.parse(birthday);
            Date now = new Date();
            long time = now.getTime() - birDate.getTime();
            long day = time / 1000 / 60 / 60 / 24;
            long week = day / 7;
            System.out.println("到现在为止经过了:" + week + "周");
        } else {
           // System.out.println("输入格式错误");
        }

    }
}

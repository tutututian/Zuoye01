package com.hp.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*
*
* 输入一个生产日期格式"yyyy-MM-dd",再输入一个数字(保质期的天数)。
* 然后经过计算输出促销日期，促销日期为:该商品过期日前2周的周三
* */
public class Test04 {
    public static void main(String[] args) throws ParseException {
        Scanner input = new Scanner(System.in);

        System.out.println("请输入生产日期(格式为yyyy-MM-dd)：");
        String production = input.nextLine();

        System.out.println("请输入保质期的天数：");
        int ExpirationDays = input.nextInt();

        long times = 24 * 60 * 60 * 1000;//一天的毫秒值

        if (production.matches("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2}")) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date productionDate = simpleDateFormat.parse(production);//把输入的日期字符串转换为日期格式
            Date nowDate = new Date();//获取当前的时间

            System.out.println("当前日期为：" + simpleDateFormat.format(nowDate));

            long pastTime = nowDate.getTime() - productionDate.getTime();//获得输入日期到现在的毫秒值
            long pastDays = pastTime / (1000 * 60 * 60 * 24);//获取天数

            //算出过期日期的毫秒值
            long pastDueTime = productionDate.getTime() + times * ExpirationDays;
            //把过期日期的毫秒值转化为日期格式
            Date pastDueDate = new Date(pastDueTime);

            System.out.println("你所购买的商品过期日期为：" + simpleDateFormat.format(pastDueDate));

            if (pastDays == ExpirationDays) { //看过去的天数是否等于保质期天数
                System.out.println("你所购买的商品已过期！");

            }else {//计算输出促销日期，促销日期为:该商品过期日前2周的周三

                //计算促销日期的毫秒值
                long promoteSaleDateTime = pastDueTime - (times * 11);
                Date promoteSaleDate = new Date(promoteSaleDateTime);

                System.out.println("该产品促销日期为" + simpleDateFormat.format(promoteSaleDate));

            }
        }else {
            System.out.println("格式不正确！");
        }

    }

   /* public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入生产日期, 输入格式为:yyyy-MM-dd");
        String date1 = sc.nextLine();
        System.out.println("请输入保质期的天数");
        String day = sc.nextLine();
        //  matches() 方法用于检测字符串是否匹配给定的正则表达式。
        if (date1.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}")) {
            //parse() 方法可解析一个日期时间字符串，并返回 1970/1/1 午夜距离该日期时间的毫秒数
            Date dayDate = sdf.parse(day);
            Date now = new Date();

            //long date = time / 1000 / 60 / 60 / 24;

            long date2= dayDate - ((2 * 7) + 4);
            long a=date1+date2;
            //29   19

            System.out.println("促销日期" + a );
        } else {
            // System.out.println("输入格式错误");
        }
    }*/
}

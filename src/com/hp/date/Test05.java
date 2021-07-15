package com.hp.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test05 {
    public static void main(String[] args) throws ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入你的身份证号(格式你懂的)：");
        String birthday = input.nextLine();

        //因为\\w单词字符是任何的字母，数字或者下划线字符,所以[^_]的作用是最后1位数可以是除掉"_"之外的单词字符
        String regex = "[\\d]{17}[\\w&&[^_]]{1}";
        Pattern pattern = Pattern.compile(regex );

        Matcher matcher = pattern.matcher(birthday);

        if (matcher.matches()) {
            //截取输入身份证的年月日
            int year = Integer.parseInt(birthday.substring(6, 10));
            int month = Integer.parseInt(birthday.substring(10, 12));
            int day = Integer.parseInt(birthday.substring(12, 14));
            System.out.println("你的生日是" + year + "年" + month + "月" + day + "日");

            //得到20岁生日当天的日期
            int TwentyBirthYear = year + 20;
            System.out.println("你的20岁生日是" + TwentyBirthYear + "年" + month + "月" + day + "日");


            //算出从生日那一年的1月1日起到20岁生日的那个月一共有多少天

            //创建一个StringBuilder对象，目的是为了把截取到的年月日拼接到一起
            StringBuilder BirthDay = new StringBuilder();

            //这里year+20是为了获取出生起20年后的生日,-month和-day前的添加“-”是因为我们到时候转化为SimpleDateFormat的格式为
            //yyyy-MM-dd，如果不加parse转换不了.
            BirthDay.append(year + 20).append(-month).append(-day);

            //因为parse只能转化为String类型，所以创一个BirthDay的String对象
            String BirthDayString = new String(BirthDay);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//日期格式

            Date BirthDate = simpleDateFormat.parse(BirthDayString);//把截取的日期字符串转换为日期格式

//			//创建一个Calendar对象。目的是为了引用DAY_OF_YEAR方法获取这一年的第几天
//			Calendar c = Calendar.getInstance();// 在主函数开始时调用，返回一个实例化对象，此对象是static的，在内存中保留着它的引
//			用，即内存中有一块区域专门用来存放静态方法和变量，可以直接使用，调用多次返回同一个对象。

//			c.setTime(BirthDate);
//			int BirthYearDays = c.get(Calendar.DAY_OF_YEAR);
//			System.out.println(BirthYearDays);

            //Date对象的getDay方法，根据本地时间，返回一个具体日期中一周的第几天。返回值从0~6，分别对应星期日~星期六。
            int week = BirthDate.getDay();

            switch (week) {
                case 0:
                    System.out.println("你的20岁生日在本周的星期天！");

                    if (day - 4 > 0) {//如果天数减去到星期三的天数大于0，说明还在本月，天数减去4天即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day-4) + "日");
                    }else{//否则，说明还在上月，月份减一，减一后的月份天数减去超出的天数即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + (month-1) + "月" + (MonthDays(year,month - 1)- Math.abs(day - 4)) + "日");
                    }
                    break;

                case 1:
                    System.out.println("你的20岁生日在本周的星期一！");
                    System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day+2) + "日");
                    break;

                case 2:
                    System.out.println("你的20岁生日在本周的星期二！");
                    System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day+1) + "日");
                    break;

                case 3:
                    System.out.println("你的20岁生日在本周的星期三！");
                    System.out.println("所在的本周的星期三的日期就是今天：" + TwentyBirthYear + "年" + month + "月" + day + "日");
                    break;

                case 4:
                    System.out.println("你的20岁生日在本周的星期四！");
                    if (day - 1 > 0) {//如果天数减去到星期三的天数大于0，说明还在本月，天数减去1天即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day-1) + "日");
                    }else{//否则，说明还在上月，月份减一，减一后的月份天数减去超出的天数即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + (month-1) + "月" + (MonthDays(year,month - 1)- Math.abs(day - 1)) + "日");
                    }
                    break;

                case 5:
                    System.out.println("你的20岁生日在本周的星期五！");
                    if (day - 2 > 0) {//如果天数减去到星期三的天数大于0，说明还在本月，天数减去2天即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day-2) + "日");
                    }else{//否则，说明还在上月，月份减一，减一后的月份天数减去超出的天数即可，Math.abs()取绝对值
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + (month-1) + "月" + (MonthDays(year,month - 1)- Math.abs(day - 2)) + "日");
                    }
                    break;

                case 6:
                    System.out.println("你的20岁生日在本周的星期六！");
                    if (day - 3 > 0) {//如果天数减去到星期三的天数大于0，说明还在本月，天数减去3天即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + month + "月" + (day-3) + "日");
                    }else{//否则，说明还在上月，月份减一，减一后的月份天数减去超出的天数即可
                        System.out.println("所在的本周的星期三的日期为：" + TwentyBirthYear + "年" + (month-1) + "月" + (MonthDays(year,month - 1)- Math.abs(day - 3)) + "日");
                    }
                    break;

            }

        }else {
            System.out.println("格式有误，请重新输入！");
        }
    }

    //用于获取每个月多少天的方法
    private static int MonthDays(int year, int month) {
        int days = 0;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:days = 31;break;//以上几月为31天
            case 4:
            case 6:
            case 9:
            case 11:days = 30;break;//4，6，9，11为30天
            case 2:
                if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                    days = 29;//闰年2月为29天
                }else {
                    days = 28;//闰年2月为28天
                }
        }

        return days;
    }
}



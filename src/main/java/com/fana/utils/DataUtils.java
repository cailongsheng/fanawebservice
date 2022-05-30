package com.fana.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DataUtils {

    /**
     * 获取当前时间
     *
     * @param format 格式 例如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat s = new SimpleDateFormat(format);
        return s.format(new Date());
    }

    public static String getYesterday(String format) {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, -24);
        return s.format(c.getTime());
    }

    public static String getDate() {

        //括号内制定格式化时间格式
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化时间 2019-09-30
        Date date = new Date();// 输出结果 Mon Sep 30 00:00:00 CST 2019
        return date.toString();


    }

    public static String getDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newTime = format.parse(time);
            return newTime.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取上一个月
     *
     * @param format 格式 例如：yyyy-MM
     */
    public static String getPreDate(String format) {
        SimpleDateFormat ss = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        return ss.format(c.getTime());
    }


    public static LocalDateTime getDateConfig(String str) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime parse = LocalDateTime.parse(str, format);
            return parse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return LocalDateTime.now();
    }

    public static String getDateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String format1 = localDateTime.format(format);
        return format1.substring(0, 10);
    }


    public static String getNumber(String barcode) {
        for (int i = 0; i < barcode.length(); i++) {
            if (!barcode.substring(i, barcode.length()).startsWith("0")) {
                return barcode.substring(i, barcode.length());
            }
        }
        return "";
    }

    public static String getDate1(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String format1 = localDateTime.format(format);
        return format1.substring(0, 10);

    }

    public static String getMobile(String mobile) {
        if (Pattern.matches("^(7)\\d{9,13}", mobile)) {
            //不否
            return "0" + mobile;
        }
        return mobile;
    }

    public static void main(String[] args) {
        String format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format("2022-05-20 19:52:14");
        System.out.println(format);


    }

    public static String getTime() {
        Date date = new Date();
        long time = System.currentTimeMillis();
        date.setTime(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String getDateTime() {
        Date date = new Date();
        long time = System.currentTimeMillis();
        date.setTime(time);
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(date);
    }

    public static String getUnixTime() {
        Date date = new Date();
        long time = System.currentTimeMillis();
        date.setTime(time);
        return new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss").format(date);
    }

    /**
     * English name specification
     *
     * @return
     */
    public static String dateFormat(String time) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date newTime = format.parse(time);
            return newTime.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
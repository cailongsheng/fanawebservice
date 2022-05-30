package com.fana.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFormatter {
    public static String getLocalDateTimeFormatter(LocalDateTime time){
        String pattern = "dd-MM-yyyy HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        System.out.println(time.format(dateTimeFormatter));
        System.out.println(dateTimeFormatter.format(time));
        return dateTimeFormatter.format(time);
    }

    public static void main(String[] args) {
        getLocalDateTimeFormatter(LocalDateTime.now());
    }
}

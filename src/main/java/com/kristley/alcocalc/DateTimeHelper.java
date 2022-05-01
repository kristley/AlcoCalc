package com.kristley.alcocalc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static LocalDateTime timeFromString(String time){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.from(format.parse(time));
    }

    public static String timeStringFromDate(LocalDateTime date){
        return dateToString(date, "yyyy-MM-dd HH:mm");
    }

    public static String prettyDateStringFromDate(LocalDateTime date){
        return dateToString(date, "d MMMM yy");
    }

    public static String prettyMonthDayStringFromDate(LocalDateTime date){
        return dateToString(date, "d MMMM");
    }

    private static String dateToString(LocalDateTime date, String format){
        return date.format(DateTimeFormatter.ofPattern(format));
    }
    public static String now() {
        return DateTimeHelper.timeStringFromDate(LocalDateTime.now());
    }

    public static String getTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}

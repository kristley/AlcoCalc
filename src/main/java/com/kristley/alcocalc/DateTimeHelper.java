package com.kristley.alcocalc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static LocalDateTime timeFromString(String time){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.from(format.parse(time));
    }

    public static String timeStringFromDate(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

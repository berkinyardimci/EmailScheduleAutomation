package com.emailschedule.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertToStringFromLocalDateTime {
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }
}

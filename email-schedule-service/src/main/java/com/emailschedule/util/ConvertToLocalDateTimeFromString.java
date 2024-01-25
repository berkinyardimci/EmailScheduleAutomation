package com.emailschedule.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertToLocalDateTimeFromString {
    public static LocalDateTime parseStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }
}

package com.emailschedule.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDate {

    //Farklı ülkelerde nasıl handle edebiliriz
    //farklı time zonelarda
    //requestten hangi time zone alabiliriz.
    public static LocalDateTime parseStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }
}

package com.emailschedule.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDate {

    //Farklı ülkelerde nasıl handle edebiliriz
    //farklı time zonelarda
    //requestten hangi time zone alabiliriz.
    public static LocalDateTime parseStringToLocalDateTime(String sendingDate, String sendingTime) {
        String willSendDate = sendingDate + " " + sendingTime;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(willSendDate, dateTimeFormatter);
    }

    public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }
}

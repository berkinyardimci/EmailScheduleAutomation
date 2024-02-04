package com.emailschedule.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatMessage {

    public static String scheduleEmailResponseMessage(String email,String date){
        return String.format("'%s' email to be sent on %s", email, date);
    }

    public static String cancelScheduleEmailResponseMessage(String email){
        return String.format("The email intended to be sent to '%s' has been canceled.", email);
    }
}

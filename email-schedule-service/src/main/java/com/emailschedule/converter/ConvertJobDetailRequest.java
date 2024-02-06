package com.emailschedule.converter;

import com.emailschedule.dto.request.JobDetailRequest;
import com.emailschedule.dto.request.ScheduleEmailRequest;
import org.quartz.JobKey;

public class ConvertJobDetailRequest {

    public static JobDetailRequest convertJobDetailRequest(ScheduleEmailRequest emailRequest, JobKey jobKey, String email) {
        return JobDetailRequest.builder()
                .emailReceiver(emailRequest.getEmailReceiver())
                .emailSender(email)
                .subject(emailRequest.getSubject())
                .content(emailRequest.getContent())
                .emailSender(email)
                .cc(emailRequest.getCc())
                .bc(emailRequest.getBc())
                .jobKey(jobKey)
                .build();
    }
}

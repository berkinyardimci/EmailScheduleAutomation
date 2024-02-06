package com.emailschedule.converter;


import com.emailschedule.dto.response.FindAllScheduledEmailResponse;
import com.emailschedule.entity.ScheduledEmail;

public class ConvertFindAllScheduleResponse {

    public static FindAllScheduledEmailResponse convertToFindAllScheduledEmail(ScheduledEmail scheduledEmail) {
        return FindAllScheduledEmailResponse.builder()
                .id(scheduledEmail.getId())
                .emailReceiver(scheduledEmail.getEmailReceiver())
                .subject(scheduledEmail.getSubject())
                .content(scheduledEmail.getContent())
                .sendingDate(scheduledEmail.getSendingDate())
                .cc(scheduledEmail.getCc())
                .bc(scheduledEmail.getBc())
                .build();
    }
}

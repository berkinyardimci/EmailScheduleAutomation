package com.emailschedule.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.quartz.JobKey;

import java.util.List;

@Getter
@Builder
public class JobDetailRequest {

    private String emailSender;
    private String emailReceiver;
    private String subject;
    private String content;
    private String sendingDate;
    private List<String> cc;
    private List<String> bc;
    private JobKey jobKey;
}

package com.emailschedule.service;

import com.emailschedule.model.ScheduledEmailModel;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

@RequiredArgsConstructor
public class EmailJob implements Job {

    private final EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        String emailSender = (String) jobDataMap.get("emailSender");
        String emailReceiver = (String) jobDataMap.get("emailReceiver");
        String subject = (String) jobDataMap.get("subject");
        String content = (String) jobDataMap.get("content");
        List<String> cc = (List<String>) jobDataMap.get("cc");
        List<String> bcc = (List<String>) jobDataMap.get("bcc");

        ScheduledEmailModel scheduledEmailModel = ScheduledEmailModel.builder()
                .emailSender(emailSender)
                .emailReceiver(emailReceiver)
                .subject(subject)
                .content(content)
                .cc(cc)
                .bcc(bcc)
                .build();

        emailService.sendEmail(scheduledEmailModel);
    }
}

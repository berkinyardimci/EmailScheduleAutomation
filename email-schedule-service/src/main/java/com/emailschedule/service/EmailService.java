package com.emailschedule.service;

import com.emailschedule.client.SendEmailClient;
import com.emailschedule.model.ScheduledEmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SendEmailClient sendEmailClient;

    public void sendEmail(ScheduledEmailModel scheduledEmailModel) {

        sendEmailClient.sendScheduledEmail(scheduledEmailModel);
        System.out.println("Gönderen " + scheduledEmailModel.getEmailSender() +
                " Alıcı " + scheduledEmailModel.getEmailReceiver() +
                " Konu: " + scheduledEmailModel.getContent());
    }
}

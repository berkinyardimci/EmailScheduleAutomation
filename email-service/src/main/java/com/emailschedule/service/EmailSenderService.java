package com.emailschedule.service;

import com.emailschedule.model.SendEmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

   private final JavaMailSender javaMailSender;

    public void sendMail(SendEmailModel sendEmailModel) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sendEmailModel.getSender());
        mailMessage.setTo(sendEmailModel.getEmailReceiver());
        mailMessage.setSubject(sendEmailModel.getSubject());
        mailMessage.setText(sendEmailModel.getContent());

        mailMessage.setCc(convertListToArray(sendEmailModel.getCc()));
        mailMessage.setBcc(convertListToArray(sendEmailModel.getBc()));

        javaMailSender.send(mailMessage);
    }

    public static String[] convertListToArray(List<String> stringList) {
        if (stringList == null) {
            return new String[0];
        }
        String[] stringArray = new String[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            stringArray[i] = stringList.get(i);
        }

        return stringArray;
    }
}

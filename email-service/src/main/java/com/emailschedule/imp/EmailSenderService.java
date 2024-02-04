package com.emailschedule.imp;

import com.emailschedule.model.ScheduledEmailModel;
import com.emailschedule.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailService {

   private final JavaMailSender javaMailSender;

    @Override
    public void sendCustomEmail(ScheduledEmailModel sendEmailModel) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sendEmailModel.getSender());
        mailMessage.setTo(sendEmailModel.getEmailReceiver());
        mailMessage.setSubject(sendEmailModel.getSubject());
        mailMessage.setText(sendEmailModel.getContent());

        mailMessage.setCc(convertListToArray(sendEmailModel.getCc()));
        mailMessage.setBcc(convertListToArray(sendEmailModel.getBc()));

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendPasswordReset(String email) {
        //Todo: Giriş İşlemi için şifre gönderme
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

package com.emailschedule.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {


    public void sendEmail(String userEmail, String subject, String content, List<String> cc, List<String> bcc) {
        System.out.println("Selamm " + userEmail + " Subject " + subject + " Content: " + content);
        cc.forEach(System.out::println);
        bcc.forEach(System.out::println);
    }
}

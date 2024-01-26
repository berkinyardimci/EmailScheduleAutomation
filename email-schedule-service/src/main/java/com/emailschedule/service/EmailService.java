package com.emailschedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RestTemplate restTemplate;

    public void sendEmail(String userEmail, String subject, String content, List<String> cc, List<String> bcc) {
        System.out.println("Selamm " + userEmail + " Subject " + subject + " Content: " + content);

    }
}

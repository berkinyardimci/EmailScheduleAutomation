package com.emailschedule.controller;

import com.emailschedule.model.ScheduledEmailModel;
import com.emailschedule.imp.EmailSenderService;
import com.emailschedule.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sendEmail")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendScheduledEmail(@RequestBody ScheduledEmailModel sendEmailModel){
        emailService.sendCustomEmail(sendEmailModel);

        return ResponseEntity.ok("ok");
    }
    @PostMapping("/send/login")
    public ResponseEntity<String> sendPasswordReset(String email){
        emailService.sendPasswordReset(email);
        return ResponseEntity.ok("ok");
    }
}

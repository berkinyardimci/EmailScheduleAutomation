package com.emailschedule.controller;

import com.emailschedule.model.SendEmailModel;
import com.emailschedule.service.EmailSenderService;
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

    private final EmailSenderService emailSenderService;


    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailModel sendEmailModel){
        emailSenderService.sendMail(sendEmailModel);

        return ResponseEntity.ok("ok");
    }
}

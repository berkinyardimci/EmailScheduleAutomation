package com.emailschedule.client;

import com.emailschedule.model.ScheduledEmailModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9093/sendEmail", name = "schedule-email")
public interface SendEmailClient {

    @PostMapping("/send")
    ResponseEntity<String> sendScheduledEmail(@RequestBody ScheduledEmailModel sendEmailModel);
}

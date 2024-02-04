package com.emailschedule.controller;

import com.emailschedule.dto.request.CancelRequest;
import com.emailschedule.dto.request.FindAllRequest;
import com.emailschedule.dto.request.ScheduleEmailRequest;
import com.emailschedule.dto.response.CancelScheduleResponse;
import com.emailschedule.dto.response.FindAllScheduledEmailResponse;
import com.emailschedule.dto.response.ScheduleEmailResponse;
import com.emailschedule.service.ScheduleEmailService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class ScheduleEmailController {

    private final ScheduleEmailService scheduleEmailService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@RequestBody ScheduleEmailRequest emailRequest) {
        try {
            ScheduleEmailResponse response = scheduleEmailService.scheduleEmailSending(emailRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Id yi clientten nasıl alalım?
    //ne döndürebiliriz
    @PostMapping("/cancel")
    public void cancelSchedule(@RequestBody CancelRequest request) throws SchedulerException {
        scheduleEmailService.cancelEmailSending(request.getScheduledEmailId());
    }

    //TODO: id yerine email alacaz
    @GetMapping("/status")
    public ResponseEntity<List<FindAllScheduledEmailResponse>> getAllScheduledEmailsByStatus(@RequestBody FindAllRequest findAllRequest) {
        List<FindAllScheduledEmailResponse> scheduledEmails = scheduleEmailService.findAllScheduledEmailByStatus(findAllRequest);
        return new ResponseEntity<>(scheduledEmails, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<String> gets() {
        return ResponseEntity.ok("ok");
    }
}
package com.emailschedule.controller;

import com.emailschedule.dto.request.ScheduleEmailRequest;
import com.emailschedule.dto.response.CancelScheduleResponse;
import com.emailschedule.dto.response.FindAllScheduledEmailResponse;
import com.emailschedule.dto.response.ScheduleEmailResponse;
import com.emailschedule.entity.enums.Status;
import com.emailschedule.service.ScheduleEmailService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@RequestBody ScheduleEmailRequest emailRequest,@RequestHeader("loggedInEmail") String email) {
        try {
            ScheduleEmailResponse response = scheduleEmailService.scheduleEmailSending(emailRequest,email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Patche çevircez
    //path variable
    @PatchMapping("/cancel/{scheduleId}")
    public ResponseEntity<CancelScheduleResponse> cancelSchedule(@PathVariable Long scheduleId) throws SchedulerException {
        CancelScheduleResponse cancelScheduleResponse = scheduleEmailService.cancelEmailSending(scheduleId);
        return new ResponseEntity<>(cancelScheduleResponse, HttpStatus.OK);
    }

    //TODO: id yerine email alacaz
    @GetMapping("/status")
    public ResponseEntity<List<FindAllScheduledEmailResponse>> getAllScheduledEmailsByStatus(@RequestParam Status status, @RequestHeader("loggedInEmail") String email) {
        List<FindAllScheduledEmailResponse> scheduledEmails = scheduleEmailService.findAllScheduledEmailByStatus(status,email);
        return new ResponseEntity<>(scheduledEmails, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<String> gets() {
        return ResponseEntity.ok("ok");
    }
}
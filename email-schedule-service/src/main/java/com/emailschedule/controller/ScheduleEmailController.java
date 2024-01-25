package com.emailschedule.controller;

import com.emailschedule.dto.request.CancelRequest;
import com.emailschedule.dto.request.FindAllRequest;
import com.emailschedule.dto.request.ScheduleEmailRequest;
import com.emailschedule.dto.response.FindAllScheduledEmailResponse;
import com.emailschedule.service.ScheduleEmailService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class ScheduleEmailController {
    private final ScheduleEmailService scheduleEmailService;

    @PostMapping("/schedule")
    public void scheduleEmail(@RequestBody ScheduleEmailRequest emailRequest) {
        try {
            scheduleEmailService.scheduleEmailSending(emailRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/cancel")
    public void cancelSchedule(@RequestBody CancelRequest request) throws SchedulerException {
        scheduleEmailService.cancelEmailSending(request.getScheduledEmailId());
    }

    @GetMapping("/status")
    public List<FindAllScheduledEmailResponse> getAllScheduledEmailsByStatus(@RequestBody FindAllRequest findAllRequest) {
        return scheduleEmailService.findAllScheduledEmailByStatus(findAllRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<String> gets() {
        return ResponseEntity.ok("ok");
    }
}
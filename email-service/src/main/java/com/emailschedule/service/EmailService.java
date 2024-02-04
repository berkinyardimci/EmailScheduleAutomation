package com.emailschedule.service;

import com.emailschedule.model.ScheduledEmailModel;

public interface EmailService {
    void sendPasswordReset(String email);
    void sendCustomEmail(ScheduledEmailModel sendEmailModel);
}

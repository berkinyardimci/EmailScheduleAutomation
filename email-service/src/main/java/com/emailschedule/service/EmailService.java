package com.emailschedule.service;

import com.emailschedule.model.ScheduledEmailModel;

public interface EmailService {

    //TODO: sendPasswordResetEmail
    void sendPasswordReset(String email);
    void sendCustomEmail(ScheduledEmailModel sendEmailModel);
}

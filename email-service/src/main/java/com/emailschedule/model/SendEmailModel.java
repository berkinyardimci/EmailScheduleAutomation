package com.emailschedule.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SendEmailModel {
    private String sender;
    private String emailReceiver;
    private String subject;
    private String content;
    private List<String> cc;
    private List<String> bc;
}

package com.emailschedule.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindAllScheduledEmailResponse {
    private String emailReceiver;
    private String subject;
    private String content;
    private String sendingDate;
    private List<String> cc;
    private List<String> bc;
}

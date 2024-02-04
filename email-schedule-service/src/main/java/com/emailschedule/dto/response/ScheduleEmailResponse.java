package com.emailschedule.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleEmailResponse {

    private String receiverEmail;

    private String sendingDate;

    private String message;

}

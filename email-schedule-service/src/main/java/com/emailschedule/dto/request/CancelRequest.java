package com.emailschedule.dto.request;

import lombok.Getter;

@Getter
public class CancelRequest {
    private Long scheduledEmailId;
}
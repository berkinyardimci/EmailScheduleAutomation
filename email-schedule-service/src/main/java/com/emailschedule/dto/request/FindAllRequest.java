package com.emailschedule.dto.request;

import com.emailschedule.entity.enums.Status;
import lombok.Data;

@Data
public class FindAllRequest {
    private Long senderId;
    private Status status;
}

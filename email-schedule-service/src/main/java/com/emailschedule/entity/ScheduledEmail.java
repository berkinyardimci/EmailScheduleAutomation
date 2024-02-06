package com.emailschedule.entity;

import com.emailschedule.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private String senderEmail;
    private String emailReceiver;
    private String subject;
    private String content;
    private String sendingDate;
    private String jobKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    private List<String> cc;
    @ElementCollection
    private List<String> bc;
}

package com.emailschedule.repository;

import com.emailschedule.entity.ScheduledEmail;
import com.emailschedule.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledEmailRepository extends JpaRepository<ScheduledEmail,Long> {

    List<ScheduledEmail> findAllByStatusAndSenderEmail(Status status, String email);
}


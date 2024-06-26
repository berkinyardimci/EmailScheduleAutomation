package com.emailschedule.service;

import com.emailschedule.converter.ConvertFindAllScheduleResponse;
import com.emailschedule.converter.ConvertJobDetailRequest;
import com.emailschedule.dto.request.JobDetailRequest;
import com.emailschedule.dto.request.ScheduleEmailRequest;
import com.emailschedule.dto.response.CancelScheduleResponse;
import com.emailschedule.dto.response.FindAllScheduledEmailResponse;
import com.emailschedule.dto.response.ScheduleEmailResponse;
import com.emailschedule.entity.ScheduledEmail;
import com.emailschedule.entity.enums.Status;
import com.emailschedule.repository.ScheduledEmailRepository;
import com.emailschedule.util.ConvertDate;
import com.emailschedule.util.CreateRandomJobKey;
import com.emailschedule.util.FormatMessage;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScheduleEmailService {

    private final Scheduler scheduler;
    private final ScheduledEmailRepository scheduledEmailRepository;

    public ScheduleEmailResponse scheduleEmailSending(ScheduleEmailRequest emailRequest, String email) throws SchedulerException {

        LocalDateTime localDateTime = ConvertDate.parseStringToLocalDateTime(emailRequest.getSendingDate(),emailRequest.getSendingTime());

        JobKey jobKey = new JobKey(CreateRandomJobKey.generateRandomKey(), "reports-job");
        JobDetail jobDetail = buildJobDetail(ConvertJobDetailRequest.convertJobDetailRequest(emailRequest, jobKey, email));

        Trigger trigger = buildJobTrigger(jobDetail, localDateTime);
        String dateString = ConvertDate.formatLocalDateTimeToString(localDateTime);

        //convertor yaz
        //private method
        ScheduledEmail scheduledEmail = ScheduledEmail.builder()
                .senderEmail(email)
                .emailReceiver(emailRequest.getEmailReceiver())
                .content(emailRequest.getContent())
                .subject(emailRequest.getSubject())
                .sendingDate(dateString)
                .jobKey(jobKey.getName())
                .status(Status.PENDING)
                .cc(emailRequest.getCc())
                .bc(emailRequest.getBc())
                .build();

        scheduledEmailRepository.save(scheduledEmail);
        scheduler.scheduleJob(jobDetail, trigger);
        return ScheduleEmailResponse.builder()
                .receiverEmail(emailRequest.getEmailReceiver())
                .sendingDate(dateString)
                .message(FormatMessage.scheduleEmailResponseMessage(emailRequest.getEmailReceiver(), dateString))
                .build();
    }

    private JobDetail buildJobDetail(JobDetailRequest jobDetailRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("emailReceiver", jobDetailRequest.getEmailReceiver());
        jobDataMap.put("emailSender", jobDetailRequest.getEmailSender());
        jobDataMap.put("subject", jobDetailRequest.getSubject());
        jobDataMap.put("content", jobDetailRequest.getContent());
        jobDataMap.put("cc", jobDetailRequest.getCc());
        jobDataMap.put("bcc", jobDetailRequest.getBc());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(jobDetailRequest.getJobKey())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, LocalDateTime sendingDate) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "report-triggers")
                .startAt(java.util.Date.from(sendingDate.atZone(java.time.ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionNowWithExistingCount())
                .build();
    }

    //config paketine taşı
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    public CancelScheduleResponse cancelEmailSending(Long scheduledEmailId) throws SchedulerException {
        JobKey jobKey = findJobKeyByEmail(scheduledEmailId);
        if (jobKey != null) {
            scheduler.deleteJob(jobKey);
            changeStatusToCanceled(scheduledEmailId);
            return CancelScheduleResponse.builder()
                    .message("Başarılı bir şekilde iptal edilmiştir.")
                    .build();
        }
        return null;
    }

    private void changeStatusToCanceled(Long scheduledEmailId) {
        ScheduledEmail scheduledEmail = scheduledEmailRepository.findById(scheduledEmailId).orElse(null);
        scheduledEmail.setStatus(Status.CANCELLED);
        scheduledEmailRepository.save(scheduledEmail);
    }

    private JobKey findJobKeyByEmail(Long scheduledEmailId) throws SchedulerException {
        ScheduledEmail scheduledEmail = scheduledEmailRepository.findById(scheduledEmailId).orElse(null);
        return scheduler.getJobKeys(GroupMatcher.jobGroupEquals("reports-job"))
                .stream()
                .filter(jobKey -> scheduledEmail.getJobKey().equals(jobKey.getName()))
                .findFirst()
                .orElse(null);
    }

    public List<FindAllScheduledEmailResponse> findAllScheduledEmailByStatus(Status status, String email) {
        List<ScheduledEmail> scheduledEmails = scheduledEmailRepository.findAllByStatusAndSenderEmail(status, email);
        return scheduledEmails.stream()
                .map(scheduledEmail -> ConvertFindAllScheduleResponse.convertToFindAllScheduledEmail(scheduledEmail))
                .collect(Collectors.toList());
    }


}

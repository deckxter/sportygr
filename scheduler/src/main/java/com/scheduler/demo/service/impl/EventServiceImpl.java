package com.scheduler.demo.service.impl;

import com.scheduler.demo.dto.EventDto;
import com.scheduler.demo.job.UpdateEventJob;
import com.scheduler.demo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void registerStatus(EventDto eventDto) {
        log.info("Is the event live?[{}]: ", eventDto.getLive());

        if(eventDto.getLive()) {
            log.info("Register schedule for event[{}]: ", eventDto.getEventId());

            JobDetail jobDetail = buildJobDetails(eventDto.getEventId());
            Trigger trigger = buildTrigger(jobDetail);

            try {
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private JobDetail buildJobDetails(String eventId){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("eventId", eventId);

        return  JobBuilder.newJob(UpdateEventJob.class)
                .withIdentity(UUID.randomUUID().toString(),"record-jobs") // unique job key
                .withDescription("Record Job Description")
                .usingJobData(jobDataMap) // values to pass to job
                .storeDurably(true) //false - delete job once job executed
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail){

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("Update-Event-Job-1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .build();
    }
}

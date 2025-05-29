package com.sportygr.demo.service.impl;

import com.sportygr.demo.dto.EventDto;
import com.sportygr.demo.job.UpdateEventJob;
import com.sportygr.demo.service.EventService;
import com.sportygr.demo.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void registerStatus(EventDto eventDto) {
        log.info("eventDto: " +eventDto);

        JobDetail jobDetail = buildJobDetails(eventDto.getEventId());
        Trigger trigger = buildTrigger(jobDetail);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private JobDetail buildJobDetails(String eventId){
        //create map to store key-value (can be received from request) which can be passed to job
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

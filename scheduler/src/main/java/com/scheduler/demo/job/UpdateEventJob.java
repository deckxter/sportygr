package com.scheduler.demo.job;

import com.scheduler.demo.dto.EventUpdateDto;
import com.scheduler.demo.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateEventJob extends QuartzJobBean {
    @Autowired
    private KafkaProducerService kafkaProducerService;

//    @Autowired
//    private RecordRepository recordRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        String dataPassed = context.getMergedJobDataMap().get("eventId").toString();
        System.out.println("eventId : " + dataPassed);

        System.out.println("Print Records : ");
//        recordRepository.findAll().stream().forEach(System.out::println);
        //Calling REST service to fetch data about the event, and then
        //Sending this to a kafka topic

        kafkaProducerService.send(EventUpdateDto.builder().eventId(dataPassed).currentScore("0:0").build());
    }
}

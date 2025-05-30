package com.scheduler.demo.job;

import com.scheduler.demo.dto.EventUpdateDto;
import com.scheduler.demo.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class UpdateEventJob extends QuartzJobBean {
    @Value("${myapp.sports.updateserver}")
    private String updateServerUrl;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        String eventId = context.getMergedJobDataMap().get("eventId").toString();
        log.info("Looking up for information for the event[{}] ", eventId);

        RestClient restClient = RestClient.builder().build();
        EventUpdateDto eventUpdateDto = restClient.get()
                .uri(updateServerUrl, eventId)
                .retrieve()
                .body(EventUpdateDto.class);

        kafkaProducerService.send(eventUpdateDto);
    }
}

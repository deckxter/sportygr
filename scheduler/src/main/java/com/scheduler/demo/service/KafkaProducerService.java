package com.scheduler.demo.service;

import com.scheduler.demo.dto.EventUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    @Value("${myapp.sports.topicname}")
    private String topicName;

    @Autowired
    KafkaTemplate<String, EventUpdateDto> kafkaTemplate;
    public void send(EventUpdateDto value) {
        var future = kafkaTemplate.send(topicName, value);
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info(String.format("Task status send to Kafka topic : %s, Object: ", topicName)+ value);
        });
    }
}

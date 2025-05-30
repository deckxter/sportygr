package com.scheduler.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventUpdateDto {
    private String eventId;
    private Boolean live;
    private String currentScore;
}

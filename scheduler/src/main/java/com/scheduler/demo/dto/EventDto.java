package com.scheduler.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDto {
    private String eventId;
    private Boolean live;
}

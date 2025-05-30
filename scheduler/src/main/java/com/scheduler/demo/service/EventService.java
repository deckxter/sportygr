package com.scheduler.demo.service;

import com.scheduler.demo.dto.EventDto;

public interface EventService {
    void registerStatus(EventDto eventDto);
}

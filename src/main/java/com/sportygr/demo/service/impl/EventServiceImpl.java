package com.sportygr.demo.service.impl;

import com.sportygr.demo.dto.EventDto;
import com.sportygr.demo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Override
    public void registerStatus(EventDto eventDto) {
        log.info("eventDto: ", eventDto);
    }
}

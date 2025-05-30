package com.scheduler.demo.controller;

import com.scheduler.demo.dto.EventDto;
import com.scheduler.demo.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("/status")
    private void registerStatus(@RequestBody EventDto eventDto) {
        log.info("Register the event[{}] ", eventDto.getEventId());
        eventService.registerStatus(eventDto);
    }
}

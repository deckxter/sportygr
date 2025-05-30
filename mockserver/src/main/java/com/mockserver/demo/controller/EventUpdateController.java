package com.mockserver.demo.controller;

import com.mockserver.demo.dto.EventUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventUpdateController {

    @GetMapping("/{eventId}")
    public EventUpdateDto getEventUpdate(@PathVariable String eventId) {
        log.info("Update requested for the event: " +eventId);
        return EventUpdateDto.builder().eventId(eventId).live(true).currentScore("0:0").build();
    }
}

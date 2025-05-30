package com.mockserver.demo.controller;

import com.mockserver.demo.dto.EventUpdateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventUpdateController {

    @GetMapping("/{eventId}")
    public EventUpdateDto getEventUpdate(@PathVariable String eventId) {
        return EventUpdateDto.builder().eventId(eventId).live(true).currentScore("0:0").build();
    }
}

package org.psu.lab5.controller;

import org.psu.lab5.pojo.TimeResponse;
import org.psu.lab5.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("time")
public class TimeController {
    @Autowired
    private TimeService timeService;

    @GetMapping("")
    public ResponseEntity<TimeResponse> getCurrentTime() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(timeService.getCurrentUnixtime());
    }
}

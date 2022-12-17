package org.psu.lab5.service;

import org.psu.lab5.pojo.TimeResponse;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
    public TimeResponse getCurrentUnixtime() {
        Long time = System.currentTimeMillis() / 1000L;
        return new TimeResponse(time);
    }
}

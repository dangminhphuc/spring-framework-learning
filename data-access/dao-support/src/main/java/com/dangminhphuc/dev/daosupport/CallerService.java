package com.dangminhphuc.dev.daosupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallerService {
    private final Caller caller;

    @Autowired
    public CallerService(Caller caller) {
        this.caller = caller;
    }

}

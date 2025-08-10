package com.dangminhphuc.dev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GammaService {
    private final Transactionaly fooService;
    private final Transactionaly barService;

    @Autowired
    public GammaService(@Qualifier("fooService") Transactionaly fooService, @Qualifier("barService") Transactionaly barService) {
        this.fooService = fooService;
        this.barService = barService;
    }


    @Transactional
    public void executeBothSucceeds() {
        this.fooService.required_succeed();
        this.barService.required_succeed();
    }

    @Transactional
    public void executeOnlyOneSucceeds() {
        this.fooService.required_succeed();
        this.barService.required_failure();
    }
}

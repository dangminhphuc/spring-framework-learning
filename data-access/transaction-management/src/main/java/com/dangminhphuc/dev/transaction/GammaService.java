package com.dangminhphuc.dev.transaction;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GammaService {
    private final Transaction fooService;
    private final Transaction barService;

    @Getter
    private final List<String> steps = new ArrayList<>();

    @Autowired
    public GammaService(@Qualifier("fooService") Transaction fooService,
                        @Qualifier("barService") Transaction barService) {
        this.fooService = fooService;
        this.barService = barService;
    }

    @Transactional
    public void REQUIRED_execBothTxSucceed() {
        this.fooService.PROPAGATION_REQUIRED(true);
        this.barService.PROPAGATION_REQUIRED(true);
    }

    @Transactional
    public void REQUIRED_execOnly1TxSucceed() {
        this.fooService.PROPAGATION_REQUIRED(true);
        this.fooService.PROPAGATION_REQUIRED(false);
    }

    @Transactional
    public void REQUIRED_NEW_execBothTxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(true);
        this.steps.add("FINISH");
    }

    @Transactional
    public void REQUIRED_NEW_execOnly1TxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(false);
        this.steps.add("FINISH");
    }

    @Transactional
    public void NESTED_execBothTxSucceed() {
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(true);
    }

    @Transactional
    public void NESTED_execOnly1TxSucceed() {
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(false);
    }
}

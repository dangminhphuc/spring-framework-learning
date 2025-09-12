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

    public void clearSteps() {
        this.steps.clear();
    }

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
    public void REQUIRED_NEW_BothTxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(true);
        this.steps.add("FINISH");
    }

    @Transactional
    public void REQUIRED_NEW_Only1TxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_REQUIRED_NEW(true);
        this.barService.PROPAGATION_REQUIRED_NEW(false);
        this.steps.add("FINISH");
    }

    @Transactional
    public void NESTED_bothTxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_NESTED(true);
        this.barService.PROPAGATION_NESTED(true);
        this.steps.add("FINISH");
    }

    @Transactional
    public void NESTED_only1TxSucceed() {
        this.steps.add("START");
        this.fooService.PROPAGATION_NESTED(true); // This is a savepoint, so it will commit
        this.barService.PROPAGATION_NESTED(false);// If this fails, it will roll back to the savepoint
        this.steps.add("FINISH");
    }
}

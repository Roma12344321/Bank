package com.martynov.spring.scheduler;

import com.martynov.spring.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final BankAccountService bankAccountService;

    @Scheduled(fixedRate = 60000)
    public void updateAccountBalances() {
        bankAccountService.updateBalances();
    }
}

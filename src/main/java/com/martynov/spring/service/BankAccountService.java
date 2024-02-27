package com.martynov.spring.service;

import com.martynov.spring.models.BankAccount;
import com.martynov.spring.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    @Transactional
    public void save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
    @Transactional
    public void updateBalances() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            double newSum = account.getSum() * 1.05;
            double maxSum = account.getSum() * 2.07;
            if (newSum > maxSum) {
                newSum = maxSum;
            }
            account.setSum(newSum);
            bankAccountRepository.save(account);
        }
    }
}

